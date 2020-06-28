package br.com.climb.webserver.tcpclient;

import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.Response;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class TCPClient {

    private static int TIMEOUT = 1000;
    private static String HOSTNAME = "127.0.0.1";
    private static int PORT = 1234;

    private final ClientHandler clientHandler = new ClientHandler();

    private NioSocketConnector connector;
    private IoSession session;

    public void run() throws InterruptedException {

        connector = new NioSocketConnector();

        connector.setConnectTimeoutMillis(TIMEOUT);

        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        connector.getFilterChain().addLast("logger", new LoggingFilter());

        connector.setHandler(clientHandler);

        // Cria sessao
        session = connect(connector);
        sendCommands(session);

    }

    public Response getResponse() {

        while (!ClientHandler.received) {
            try {
                Thread.sleep(0,50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return clientHandler.getResponse();

    }


    /*
     *  Conexao com o server
     */
    private IoSession connect(final NioSocketConnector connector)
            throws InterruptedException {
        IoSession session = null;
        try {
            ConnectFuture future = connector.connect(new
                    InetSocketAddress(HOSTNAME, PORT));
            future.awaitUninterruptibly();
            session = future.getSession();
        } catch (RuntimeIoException e) {
            System.err.println("Failed to connect.");
            e.printStackTrace();
        }
        return session;
    }

    /*
     *  Envia comando do teclado ao servidor
     */
    private void sendCommands(final IoSession session) {

        String data = "{\n" +
                "    \"id\": 13065,\n" +
                "    \"nome\": \"thiago\",\n" +
                "    \"idade\": 33,\n" +
                "    \"altura\": 177.0,\n" +
                "    \"peso\": 36.0,\n" +
                "    \"casado\": true\n" +
                "}";

        ObjectRequest request = new ObjectRequest(
                "GET","/get/id/30/",
                "application/json",new HashMap<>(),
                data.getBytes());

        session.write(request);
    }

    /*
     *  Encerra conexao
     */
    public void close() {
        if (session != null) {
            if (session.isConnected()) {
                session.close(false);
                session.getCloseFuture().awaitUninterruptibly();
            }
        }
        connector.dispose();
    }

    public static void main(String[] args) {
        try {
            TCPClient client = new TCPClient();
            client.run();
            Response objectResponse = client.getResponse();
            client.close();
            System.out.println(objectResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}