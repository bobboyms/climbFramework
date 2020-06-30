package br.com.climb.commons.generictcpclient;

public interface TcpClientHandler<T> {

    T getResponse();

}
