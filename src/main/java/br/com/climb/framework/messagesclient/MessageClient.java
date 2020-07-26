package br.com.climb.framework.messagesclient;

import br.com.climb.commons.execptions.NotConnectionException;

public interface MessageClient {

    void sendMessage(Object message) throws NotConnectionException;

}
