package br.com.climb.rpc;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.Message;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.framework.exceptions.MethodCallException;
import br.com.climb.rpc.send.GetHandler;
import br.com.climb.rpc.send.GetRequestRpc;
import br.com.climb.rpc.send.SendRequestRpc;
import br.com.climb.rpc.send.SendtHandler;

import java.util.ArrayList;
import java.util.UUID;

public class RpcSendManager implements RpcMethod {

    private final String methodName;
    private final String controllerName;
    private final ConfigFile configFile;

    public RpcSendManager(String controllerName, String methodName, ConfigFile configFile) {
        this.methodName = methodName;
        this.controllerName = controllerName;
        this.configFile = configFile;
    }

    private String sendMessage(Object[] args) throws MethodCallException {

        final String uuid = UUID.randomUUID().toString();

        final TcpClient sendRequestRpc = new SendRequestRpc(new SendtHandler(), configFile.getMessageIp(),new Integer(configFile.getMessagePort()));
        final String finalName = controllerName+"$$"+methodName;
        sendRequestRpc.sendRequest(new RpcRequest(uuid, finalName, Message.TYPE_RPC, args));

        final Integer response = (Integer) sendRequestRpc.getResponse();
        sendRequestRpc.closeConnection();

        if (response == null || response != 200) {
            throw new MethodCallException("server error when calling function " + methodName);
        }

        return uuid;
    }

    private Object waitMessage(String uuid) throws MethodCallException {

        RpcResponse rpcResponse = null;
        int count = 0;
        boolean received = false;
        while (!received) {

            try {
                count++;
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            System.out.println("contador: " + count);

            if (count == 3700) {
                throw new MethodCallException("answer exceeded the time limit. Time miles limit = " + 30000);
            }

            final TcpClient getRequestRpc = new GetRequestRpc(new GetHandler(), "127.0.0.1",3254);
            getRequestRpc.sendRequest(new KeyRpc(uuid, KeyRpc.TYPE_GET_RESPONSE_ONE, Message.TYPE_RPC, new ArrayList<>()));
            final Object obj = getRequestRpc.getResponse();
            getRequestRpc.closeConnection();

            if (obj.getClass() == Integer.class) {
                throw new MethodCallException("error on the server while waiting for a response. Method: " + methodName);
            }

            if (obj.getClass() == RpcResponse.class) {

                rpcResponse = (RpcResponse)obj;
                if (rpcResponse.getStatusCode() == 400) {
                    continue;
                }

                if (rpcResponse.getStatusCode() == 200) {
                    received = true;
                }
            }
        }

        return rpcResponse;
    }

    @Override
    public Object methodCall(Object[] args) throws MethodCallException {
        return waitMessage(sendMessage(args));
    }

}
