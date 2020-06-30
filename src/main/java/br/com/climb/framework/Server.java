package br.com.climb.framework;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.DiscoveryRequestObject;
import br.com.climb.commons.model.DiscoveryResponse;
import br.com.climb.commons.url.Methods;
import br.com.climb.framework.clientdiscovery.ClientHandler;
import br.com.climb.framework.clientdiscovery.DiscoveryClient;
import br.com.climb.framework.requestresponse.LoaderClassRestController;
import br.com.climb.framework.requestresponse.interfaces.Storage;
import br.com.climb.framework.tcpserver.ServerHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static br.com.climb.commons.utils.ReflectionUtils.getAnnotedClass;

public class Server {

    private static final int PORT = 1234;

    public static ContainerInitializer containerInitializer;
    public static ConfigFile configFile;

    private static void loadConfigurations() throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");

    }

    private static void loadContainerInitializer() {
        containerInitializer = ContainerInitializer.newInstance(configFile);
    }

    public static void main( String[] args ) throws Exception {

        loadConfigurations();
        loadContainerInitializer();

        final Set<Class<?>> clazzs = getAnnotedClass(RestController.class, "br.com.");
        final Storage storage = new LoaderClassRestController();
        storage.storage(clazzs);

        //************

        final DiscoveryRequestObject discoveryObject = new DiscoveryRequestObject();
        discoveryObject.setUrls(new HashMap<>());

        final Set<String> urlsGet = new HashSet<>();
        Methods.GET.forEach((url, method) -> {
            urlsGet.add(url);
        });
        discoveryObject.getUrls().put("GET", urlsGet);

        final Set<String> urlsPost = new HashSet<>();
        Methods.POST.forEach((url, method) -> {
            urlsPost.add(url);
        });
        discoveryObject.getUrls().put("POST", urlsPost);

        final Set<String> urlsPut = new HashSet<>();
        Methods.PUT.forEach((url, method) -> {
            urlsPut.add(url);
        });
        discoveryObject.getUrls().put("PUT", urlsPut);

        final Set<String> urlsDelete = new HashSet<>();
        Methods.DELETE.forEach((url, method) -> {
            urlsDelete.add(url);
        });
        discoveryObject.getUrls().put("DELETE", urlsDelete);

        TcpClient discoveryClient = new DiscoveryClient(new ClientHandler(), "127.0.0.1",3030);
        discoveryClient.sendRequest(discoveryObject);
        DiscoveryResponse discoveryResponse = (DiscoveryResponse) discoveryClient.getResponse();
        discoveryClient.closeConnection();

        System.out.println("Resposta:" + discoveryResponse);

        //************

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(PORT));
    }

}
