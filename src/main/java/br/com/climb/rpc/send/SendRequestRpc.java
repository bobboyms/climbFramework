package br.com.climb.rpc.send;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.SendMessage;
import br.com.climb.commons.model.rpc.RpcRequest;

public class SendRequestRpc extends GenericTcpClient<RpcRequest> {

    public SendRequestRpc(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {

//        TcpClient discoveryClient = new SendRequestRpc(new SendtHandler(), "127.0.0.1",3254);
//        discoveryClient.sendRequest(new SendMessage("cliente", "Olá mundo"));
//        Integer response = (Integer) discoveryClient.getResponse();
//
//        discoveryClient.closeConnection();
//        System.out.println("Resposta:" + response);


    }
}