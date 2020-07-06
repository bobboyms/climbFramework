package br.com.climb.framework.clientdiscovery;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.model.DiscoveryResponse;

public class DiscoveryClient extends GenericTcpClient<DiscoveryRequest> {

    public DiscoveryClient(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {

//        TcpClient discoveryClient = new DiscoveryClient(new ClientHandler(), "127.0.0.1",3030);
//        discoveryClient.sendRequest(null);
//        DiscoveryResponse discoveryResponse = (DiscoveryResponse) discoveryClient.getResponse();
//
//        System.out.println("Resposta:" + discoveryResponse);


    }
}