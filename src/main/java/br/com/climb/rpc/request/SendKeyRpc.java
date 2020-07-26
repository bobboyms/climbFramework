package br.com.climb.rpc.request;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;

import java.util.ArrayList;
import java.util.List;

public class SendKeyRpc extends GenericTcpClient<KeyRpc> {

    public SendKeyRpc(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {

//        TcpClient discoveryClient = new SendKeyRpc(new GetKeyHandler(), "127.0.0.1",3254);
//        discoveryClient.sendRequest(new KeyRpc("", KeyRpc.TYPE_GET_RESPONSE_LIST, new ArrayList<>()));
//        List<RpcRequest> rpcRequests = (List<RpcRequest>) discoveryClient.getResponse();
//
//        discoveryClient.closeConnection();
////        System.out.println("Resposta:" + response);


    }
}