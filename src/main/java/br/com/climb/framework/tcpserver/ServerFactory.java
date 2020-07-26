package br.com.climb.framework.tcpserver;

import br.com.climb.commons.configuration.ConfigFile;

public class ServerFactory {

    public static TcpServer createWebServer(ConfigFile configFile) {
        return new Server(configFile);
    }

}
