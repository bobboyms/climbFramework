package br.com.climb.framework.tcpserver;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.ObjectResponse;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.framework.ClimbApplication;
import br.com.climb.framework.requestresponse.LoaderMethodRestController;
import br.com.climb.framework.requestresponse.interfaces.LoaderMethod;
import br.com.climb.framework.requestresponse.model.Capsule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntryPointCdi implements EntryPoint {

    @Override
    public void exec(final IoSession session, final Object message) {
        new Thread(new CallControllerParallel(session, message)).start();
    }

    public static class CallControllerParallel implements Runnable {

        private static final Logger logger = LoggerFactory.getLogger(CallControllerParallel.class);

        private final IoSession session;
        private final Object message;

        public CallControllerParallel(IoSession session, Object message) {
            this.session = session;
            this.message = message;
        }

        private void callController() {

            final Request request = (ObjectRequest)message;
            final ObjectResponse response = new ObjectResponse();

            try {

                final LoaderMethod loaderMethod = new LoaderMethodRestController();
                final Capsule capsule = loaderMethod.getMethodForCall(request);

                try(final ManagerContext context = ClimbApplication.containerInitializer.createManager()) {

                    final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass(), request.getSessionId());
                    final Object result = capsule.getMethod().invoke(instance, capsule.getArgs());

                    if (result != null) {

                        final ObjectMapper mapper = new ObjectMapper();
                        final String json = mapper.writeValueAsString(result);

                        response.setContentType("application/json; charset=UTF-8;");
                        response.setCharacterEncoding("UTF-8");
                        response.setStatus(200);
                        response.setBody(json.getBytes());

                    } else {
                        response.setStatus(200);
                    }

                    session.write(response);
                    session.closeOnFlush();

                } catch (Exception e) {
                    logger.error("responseForClient {}", e);
                    response.setStatus(500);
                    if (e != null && e.getMessage() != null) {
                        response.setBody(e.getMessage().getBytes());
                    }
                    session.write(response);
                    session.closeOnFlush();
                }

            } catch (Exception e) {
                logger.error("messageReceived {}", e);
                response.setStatus(500);
                response.setBody(e.getMessage().getBytes());
                session.write(response);
                session.closeOnFlush();
            }

        }

        @Override
        public void run() {
            callController();
        }
    }

}
