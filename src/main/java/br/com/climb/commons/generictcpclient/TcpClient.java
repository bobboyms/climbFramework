package br.com.climb.commons.generictcpclient;

public interface TcpClient<T> {

    Object getResponse();
    void sendRequest(T request);
    void closeConnection();
}
