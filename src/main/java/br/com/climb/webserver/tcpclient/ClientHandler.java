package br.com.climb.webserver.tcpclient;

import br.com.climb.commons.reqrespmodel.ObjectResponse;
import br.com.climb.commons.reqrespmodel.Response;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {

    public static boolean received = false;

    private Response response;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        System.out.println("Recebido: " +  message);
        response = (ObjectResponse)message;
        received = true;

    }

    public Response getResponse() {
        return response;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        System.out.println("deu erro");
        System.out.println(cause);

//        super.exceptionCaught(session, cause);
    }
}