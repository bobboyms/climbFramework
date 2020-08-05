package br.com.climb.framework.messagesclient;

public interface HandlerMessage<T> {
    void messageReceived(T message);
}
