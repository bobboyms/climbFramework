package br.com.climb.webserver.tcpclient;

import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.commons.reqrespmodel.Response;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class TcpClient {

    private static int TIMEOUT = 1000;
    private static String HOSTNAME = "127.0.0.1";
    private static int PORT = 1234;

    private final ClientHandler clientHandler = new ClientHandler();

    private NioSocketConnector connector;
    private IoSession session;
    private final Request request;

    public TcpClient(Request request) {
        this.request = request;
    }

    public void initialize() {
        createConnector();
        startConnection();
        sendRequest();
    }

    public Response getResponse() {

        return clientHandler.getResponse();

    }

    private void createConnector() {
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(TIMEOUT);
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.setHandler(clientHandler);
    }

    /*
     *  Conexao com o server
     */
    private void startConnection() {
        ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
        future.awaitUninterruptibly();
        session = future.getSession();
    }

    /*
     *  Envia comando do teclado ao servidor
     */
    private void sendRequest() {
        session.write(request);
    }

    /*
     *  Encerra conexao
     */
    public void closeConnection() {

        if (session != null) {
            if (session.isConnected()) {
                session.closeOnFlush().awaitUninterruptibly();
            }
        }
        connector.dispose();
    }

    public static void main(String[] args) {
        try {

            String data = "{\n" +
                    "    \"id\": 13065,\n" +
                    "    \"nome\": \"thiago\",\n" +
                    "    \"idade\": 33,\n" +
                    "    \"altura\": 177.0,\n" +
                    "    \"peso\": 36.0,\n" +
                    "    \"casado\": true\n" +
                    "}";

            Request request = new ObjectRequest(
                    "GET","/get/id/30/",
                    "application/json",new HashMap<>(),
                    data.getBytes(), "");

            TcpClient client = new TcpClient(request);
            client.initialize();
            Response objectResponse = client.getResponse();
            client.closeConnection();
            System.out.println(objectResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}