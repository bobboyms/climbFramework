package br.com.climb.commons.generictcpclient;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public abstract class GenericTcpClient<T> implements TcpClient<T> {

    private static final int TIMEOUT = 1000;
    private final GenericTcpClientHandler clientHandler;

    private final String hostname;
    private final int port;

    private NioSocketConnector connector;
    private IoSession session;

    public GenericTcpClient(GenericTcpClientHandler clientHandler, String hostname, int port) {
        this.clientHandler = clientHandler;
        this.hostname = hostname;
        this.port = port;

        initialize();
    }

    private void initialize() {
        createConnector();
        startConnection();
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
        ConnectFuture future = connector.connect(new InetSocketAddress(hostname, port));
        future.awaitUninterruptibly();
        session = future.getSession();
    }

    @Override
    public Object getResponse() {
        return clientHandler.getResponse();
    }

    /*
     *  Envia menssagem para o servidor
     */
    @Override
    public void sendRequest(T request) {
        session.write(request);
    }

    /*
     *  Encerra conexao
     */
    @Override
    public void closeConnection() {

        if (session != null) {
            if (session.isConnected()) {
                session.closeOnFlush().awaitUninterruptibly();
            }
        }
        connector.dispose();
    }

}
