package br.com.climb.framework.messagesclient;

import br.com.climb.framework.messagesclient.restclient.exceptions.CreateTopicException;
import br.com.climb.framework.messagesclient.restclient.model.TopicGetResponse;

public interface MessageClientProducer {

    void sendMessage(Object message) throws CreateTopicException;


}

