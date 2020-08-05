package br.com.climb.framework.messagesclient.restclient;

import br.com.climb.framework.messagesclient.restclient.exceptions.CreateTopicException;

public interface TopicProducer {
    void producer(Object message) throws CreateTopicException;
}
