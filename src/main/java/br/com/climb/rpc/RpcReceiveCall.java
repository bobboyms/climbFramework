package br.com.climb.rpc;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.rpc.request.GetKeyHandler;
import br.com.climb.rpc.request.SendKeyRpc;
import br.com.climb.rpc.request.SendResponseRpc;
import br.com.climb.rpc.request.SendtHandler;
import org.apache.mina.core.RuntimeIoException;

import java.util.List;

public class RpcReceiveCall implements RpcListener {

    public RpcReceiveCall(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public Integer somar(int a, int b) {
        return a + b;
    }

    private final ConfigFile configFile;

    @Override
    public void startListenerCallMethod() {

        new Thread(() -> {

            while (true) {

                try {

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final TcpClient discoveryClient = new SendKeyRpc(new GetKeyHandler(), "127.0.0.1",3254);
                    discoveryClient.sendRequest(new KeyRpc("", KeyRpc.TYPE_GET_RESPONSE_LIST));
                    Object response = discoveryClient.getResponse();
                    discoveryClient.closeConnection();

                    if (response.getClass() == Integer.class) {
                        continue;
                    }

                    final List<RpcRequest> rpcRequests = (List<RpcRequest>)response;

                    rpcRequests.forEach(rpcRequest -> {

                        //execução do methodo
                        final Object result = somar((Integer) rpcRequest.getArgs()[0], (Integer) rpcRequest.getArgs()[1]);

                        final TcpClient resp = new SendResponseRpc(new SendtHandler(), "127.0.0.1",3254);
                        resp.sendRequest(new RpcResponse(rpcRequest.getUuid(), 200 ,result));
                        resp.closeConnection();

                    });

                } catch (RuntimeIoException e) {
                    System.out.println("Não conectado ao servidor de msg");
                }
            }

        }).start();

    }

}
