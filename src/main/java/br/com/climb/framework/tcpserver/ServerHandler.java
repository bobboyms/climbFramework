package br.com.climb.framework.tcpserver;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.ObjectResponse;
import br.com.climb.commons.execptions.NotFoundException;
import br.com.climb.framework.ClimbApplication;
import br.com.climb.framework.requestresponse.LoaderMethodRestController;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.framework.requestresponse.interfaces.LoaderMethod;
import br.com.climb.framework.requestresponse.model.Capsule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        final Request request = (ObjectRequest)message;

        final ObjectResponse response = new ObjectResponse();
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

        } catch (Exception e) {
            logger.error("responseForClient {}", e);
            response.setStatus(500);
            response.setBody(e.getMessage().getBytes());
            session.write(response);
        }

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        System.out.println("---- deu erro ----");
        ObjectResponse response = new ObjectResponse();

        if (cause.getClass() == NotFoundException.class) {
            response.setStatus(400);
            response.setBody(cause.toString().getBytes());
            session.write(response);
            return;
        }

        logger.error("Erro serverHandler {}", cause);

        response.setStatus(500);
        response.setBody(cause.toString().getBytes());
        session.write(response);

    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
}
