package br.com.climb.framework.messagesclient.tcpclient.receive;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.KeyMessage;
import br.com.climb.commons.model.ReceiveMessage;

public class ReceiveMessageClient extends GenericTcpClient<KeyMessage> {

    public ReceiveMessageClient(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

}