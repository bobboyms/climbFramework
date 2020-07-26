package br.com.climb.rpc.request;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.rpc.RpcResponse;

public class SendResponseRpc extends GenericTcpClient<RpcResponse> {

    public SendResponseRpc(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {

//        TcpClient discoveryClient = new SendResponseRpc(new SendHandler(), "127.0.0.1",3254);
//        discoveryClient.sendRequest(null);
//        Integer response = (Integer) discoveryClient.getResponse();
//
//        discoveryClient.closeConnection();
//        System.out.println("Resposta:" + response);


    }
}