package br.com.climb.framework.tcpserver;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.configuration.ConfigFile;
import br.com.climb.framework.configuration.ConfigFileBean;
import br.com.climb.framework.configuration.FactoryConfigFile;
import br.com.climb.framework.execptions.ConfigFileException;
import br.com.climb.framework.requestresponse.LoaderClassRestController;
import br.com.climb.framework.requestresponse.interfaces.Storage;
import com.google.common.base.Strings;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;

import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;

public class Server {

    private static final int PORT = 1234;

    public static ContainerInitializer containerInitializer;
    public static ConfigFile configFile;

    private static void loadConfigurations() throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");

//        if (Strings.isNullOrEmpty(configFile.getPackage())) {
//            System.out.println("caiu aki: " + mainclass.getPackage().getName());
//            ((ConfigFileBean)configFile).setPackge(mainclass.getPackage().getName());
//        }

    }

    private static void loadContainerInitializer() {
        containerInitializer = ContainerInitializer.newInstance(configFile);
    }

    public static void main( String[] args ) throws Exception {

        loadConfigurations();
        loadContainerInitializer();

        final Set<Class<?>> clazzs = getAnnotedClass(RestController.class, "br.com.");
        Storage storage = new LoaderClassRestController();
        storage.storage(clazzs);

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(PORT));
    }

}
