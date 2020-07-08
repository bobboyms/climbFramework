package br.com.climb.framework.messagesclient;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.execptions.NotConnectionException;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.Message;
import br.com.climb.commons.model.SendMessage;
import br.com.climb.framework.messagesclient.tcpclient.send.ClientHandler;
import br.com.climb.framework.messagesclient.tcpclient.send.SendMessageClient;
import org.apache.mina.core.RuntimeIoException;

public class MessageClientManager implements MessageClient {

    private final String topic;
    private final ConfigFile configFile;

    public MessageClientManager(String topic, ConfigFile configFile) {
        this.topic = topic;
        this.configFile = configFile;
    }

    @Override
    public void sendMessage(Object message) throws NotConnectionException {

        try {

            TcpClient discoveryClient = new SendMessageClient(new ClientHandler(), configFile.getMessageIp(),new Integer(configFile.getMessagePort()));
            discoveryClient.sendRequest(new SendMessage(topic, Message.TYPE_MESSAGE, message));
            Integer response = (Integer) discoveryClient.getResponse();

            if (response.longValue() != 200) {
                throw new Error("Erro no servidor, mensagem não registrada");
            }

        } catch (RuntimeIoException e) {
            e.printStackTrace();
            throw new NotConnectionException("It was not possible to connect to the messaging server: " + configFile.getMessageIp() + "/" + configFile.getMessagePort());
        }



//        discoveryClient.closeConnection();
//        System.out.println("Resposta:" + response);
    }
}
