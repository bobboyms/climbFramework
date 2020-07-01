package br.com.climb.framework.tcpserver;

import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.model.DiscoveryResponse;
import br.com.climb.framework.clientdiscovery.ClientHandler;
import br.com.climb.framework.clientdiscovery.DiscoveryClient;
import br.com.climb.framework.requestresponse.LoaderClassRestController;
import br.com.climb.framework.requestresponse.interfaces.Storage;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.util.*;

import static br.com.climb.commons.utils.ReflectionUtils.getAnnotedClass;

public class Server implements TcpServer {

    public final ConfigFile configFile;

    public Server(ConfigFile configFile) {
        this.configFile = configFile;
    }

    private void initDiscoveryThread(DiscoveryRequest discoveryRequest) {
        new Thread(()->{

            while (true) {

                try {
                    final TcpClient discoveryClient = new DiscoveryClient(new ClientHandler(), configFile.getGatewayIp(), new Integer(configFile.getGatewayPort()));
                    discoveryClient.sendRequest(discoveryRequest);
                    DiscoveryResponse discoveryResponse = (DiscoveryResponse) discoveryClient.getResponse();
                    discoveryClient.closeConnection();
                } catch (RuntimeIoException e) {
                    System.out.println("Tentando se conectar ao servidor: " + configFile.getGatewayIp() + "/" + configFile.getGatewayPort());
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

    @Override
    public void start() throws Exception {
        final Set<Class<?>> clazzs = getAnnotedClass(RestController.class, configFile.getPackage());
        final Storage storage = new LoaderClassRestController();

        final DiscoveryRequest discoveryRequest = storage.storage(clazzs)
                .generateDiscoveryRequest(configFile.getLocalIp(),configFile.getLocalPort());

        initDiscoveryThread(discoveryRequest);

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(new Integer(configFile.getLocalPort())));
    }
}
