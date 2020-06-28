package br.com.climb.framework.tcpserver;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.ObjectResponse;
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

        final LoaderMethod loaderMethod = new LoaderMethodRestController();
        final Capsule capsule = loaderMethod.getMethodForCall(request);

        try(final ManagerContext context = Server.containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Object result = capsule.getMethod().invoke(instance, capsule.getArgs());

            if (result != null) {

                ObjectMapper mapper = new ObjectMapper();
                final String json = mapper.writeValueAsString(result);

                System.out.println("--------- Resposta Json ---------");
                System.out.println(json);

                ObjectResponse response = new ObjectResponse();
                response.setContentType("application/json; charset=UTF-8;");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                response.setBody(json.getBytes());

                session.write(response);

            }

        } catch (Exception e) {
            logger.error("responseForClient { }", e);
        }

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        System.out.println("deu erro");
        System.out.println(cause);

//        super.exceptionCaught(session, cause);
    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
}
