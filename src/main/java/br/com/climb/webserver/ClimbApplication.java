package br.com.climb.webserver;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.ConfigFileBean;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.framework.execptions.ConfigFileException;
import com.google.common.base.Strings;

import java.io.IOException;

public class ClimbApplication {

    public static ContainerInitializer containerInitializer;
    public static ConfigFile configFile;

    private static void loadConfigurations(Class<?> mainclass) throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");

        if (Strings.isNullOrEmpty(configFile.getPackage())) {
            System.out.println("caiu aki: " + mainclass.getPackage().getName());
            ((ConfigFileBean)configFile).setPackge(mainclass.getPackage().getName());
        }

    }

    private static void loadContainerInitializer() {
        containerInitializer = ContainerInitializer.newInstance(configFile);
    }

    private static void startWebServer() throws Exception {
        WebServer webServer = ServerFactory.createWebServer(configFile);
        webServer.start();
    }

    public static void run(Class<?> mainclass) throws Exception {
        loadConfigurations(mainclass);
        loadContainerInitializer();
        startWebServer();
    }

}
