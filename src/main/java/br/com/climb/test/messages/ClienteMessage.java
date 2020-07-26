package br.com.climb.test.messages;

import br.com.climb.framework.messagesclient.HandlerMessage;
import br.com.climb.framework.messagesclient.annotations.MessageController;

@MessageController(topicName = "cliente")
public class ClienteMessage implements HandlerMessage {

    @Override
    public void messageReceived(Object message) {
        System.out.println("Recebido via message: " + message);
    }
}
