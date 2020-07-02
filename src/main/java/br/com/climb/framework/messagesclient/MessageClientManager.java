package br.com.climb.framework.messagesclient;

import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.SendMessage;
import br.com.climb.framework.messagesclient.tcpclient.send.ClientHandler;
import br.com.climb.framework.messagesclient.tcpclient.send.SendMessageClient;

public class MessageClientManager implements MessageClient {

    private final String topic;

    public MessageClientManager(String topic) {
        this.topic = topic;
    }

    @Override
    public void sendMessage(Object message) {
        TcpClient discoveryClient = new SendMessageClient(new ClientHandler(), "127.0.0.1",3254);
        discoveryClient.sendRequest(new SendMessage(topic, message));
        Integer response = (Integer) discoveryClient.getResponse();

        if (response.longValue() != 200) {
            throw new Error("Erro no servidor, mensagem não registrada");
        }

//        discoveryClient.closeConnection();
//        System.out.println("Resposta:" + response);
    }
}
