package br.com.climb.webserver;

import br.com.climb.framework.configuration.ConfigFile;

public class ServerFactory {

    public static WebServer createWebServer(ConfigFile configFile) {
        return new JettyServer(configFile);
    }

}
