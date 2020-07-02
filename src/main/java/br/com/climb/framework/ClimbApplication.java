package br.com.climb.framework;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import br.com.climb.framework.tcpserver.ServerFactory;
import br.com.climb.framework.tcpserver.TcpServer;

import java.io.IOException;

public class ClimbApplication {

    public static ConfigFile configFile;
    public static ContainerInitializer containerInitializer;

    private static void loadConfigurations(Class<?> mainclass) throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");
    }

    private static void loadContainerInitializer() {
        containerInitializer = ContainerInitializer.newInstance(configFile);
    }

    private static void startTcpServer() throws Exception {
        TcpServer server = ServerFactory.createWebServer(configFile);
        server.start();

        System.out.println("-------------------------------------------------------");
        System.out.println("--                                                   --");
        System.out.println("--          STARTUP SERVER CLIMB FRAMEWORK           --");
        System.out.println("--                                                   --");
        System.out.println("-------------------------------------------------------");
    }

    public static void run(Class<?> mainclass) throws Exception {
        loadConfigurations(mainclass);
        loadContainerInitializer();
        startTcpServer();
    }

}
