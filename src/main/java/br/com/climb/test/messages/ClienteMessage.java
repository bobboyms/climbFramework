package br.com.climb.test.messages;

import br.com.climb.framework.messagesclient.HandlerMessage;
import br.com.climb.framework.messagesclient.annotations.MessageController;
import br.com.climb.test.model.BaixarEstoque;

@MessageController(topicName = "cliente", type = BaixarEstoque.class)
public class ClienteMessage implements HandlerMessage<BaixarEstoque> {

    @Override
    public void messageReceived(BaixarEstoque message) {
        System.out.println("Recebido via message: " + message);
    }
}
