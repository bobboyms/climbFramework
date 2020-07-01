package br.com.climb.framework;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.model.DiscoveryRequestObject;
import br.com.climb.commons.model.DiscoveryResponse;
import br.com.climb.commons.url.Methods;
import br.com.climb.framework.clientdiscovery.ClientHandler;
import br.com.climb.framework.clientdiscovery.DiscoveryClient;
import br.com.climb.framework.requestresponse.LoaderClassRestController;
import br.com.climb.framework.requestresponse.interfaces.Storage;
import br.com.climb.framework.tcpserver.ServerHandler;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.climb.commons.utils.ReflectionUtils.getAnnotedClass;

public class Server {

    public static ContainerInitializer containerInitializer;
    public static ConfigFile configFile;

    private void loadConfigurations() throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");

    }

    private void loadContainerInitializer() {
        containerInitializer = ContainerInitializer.newInstance(configFile);
    }

    public void run() throws IOException, ConfigFileException {

        loadConfigurations();
        loadContainerInitializer();

        final Set<Class<?>> clazzs = getAnnotedClass(RestController.class, configFile.getPackage());
        final Storage storage = new LoaderClassRestController();

        final DiscoveryRequest discoveryRequest = storage.storage(clazzs)
                .generateDiscoveryRequest(configFile.getLocalIp(),configFile.getLocalPort());

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

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(new Integer(configFile.getLocalPort())));
    }

    public static void main( String[] args ) throws Exception {
        new Server().run();
    }

}
