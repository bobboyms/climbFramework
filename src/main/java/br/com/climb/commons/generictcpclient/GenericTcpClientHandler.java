package br.com.climb.commons.generictcpclient;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public abstract class GenericTcpClientHandler<T> extends IoHandlerAdapter implements TcpClientHandler<T> {

    private boolean received = false;
    private T response;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("Recebido: " +  message);
        response = (T)message;
        received = true;
    }

    @Override
    public T getResponse() {

        while (!received) {
            try {
                Thread.sleep(0,50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        System.out.println("---- deu erro ----");
        System.out.println(cause.getClass());
        System.out.println(cause.getCause());
        System.out.println(cause);

//        super.exceptionCaught(session, cause);
    }

}
