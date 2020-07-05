package br.com.climb.rpc;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.framework.ClimbApplication;
import br.com.climb.framework.messagesclient.Methods;
import br.com.climb.rpc.request.GetKeyHandler;
import br.com.climb.rpc.request.SendKeyRpc;
import br.com.climb.rpc.request.SendResponseRpc;
import br.com.climb.rpc.request.SendtHandler;
import org.apache.mina.core.RuntimeIoException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RpcReceiveCall implements RpcListener {

    public RpcReceiveCall(ConfigFile configFile) {
        this.configFile = configFile;
    }

    private final ConfigFile configFile;

    @Override
    public void startListenerCallMethod() {

        new Thread(() -> {

            while (true) {

                try {

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final List<String> methodsName = Methods.RPC_CONTROLLERS.entrySet()
                            .stream().map(Map.Entry::getKey).collect(Collectors.toList());

                    final TcpClient discoveryClient = new SendKeyRpc(new GetKeyHandler(), configFile.getMessageIp(),new Integer(configFile.getMessagePort()));
                    discoveryClient.sendRequest(new KeyRpc("", KeyRpc.TYPE_GET_RESPONSE_LIST, methodsName));
                    Object response = discoveryClient.getResponse();
                    discoveryClient.closeConnection();

                    if (response.getClass() == Integer.class) {
                        continue;
                    }

                    final List<RpcRequest> rpcRequests = (List<RpcRequest>)response;

                    rpcRequests.forEach(rpcRequest -> {

                        Method method = Methods.RPC_CONTROLLERS.get(rpcRequest.getMethodName());

                        if (method != null) {

                            try(final ManagerContext context = ClimbApplication.containerInitializer.createManager()) {

                                final Object instance = context.generateInstance(method.getDeclaringClass());
                                final Object result = method.invoke(instance, rpcRequest.getArgs());

                                final TcpClient resp = new SendResponseRpc(new SendtHandler(), configFile.getMessageIp(),new Integer(configFile.getMessagePort()));
                                resp.sendRequest(new RpcResponse(rpcRequest.getUuid(), 200 ,result));
                                resp.closeConnection();

                            } catch (Exception e) {
                                e.printStackTrace();
//                                logger.error("responseForClient {}", e);
                            }

                        }

                    });


                } catch (RuntimeIoException e) {
//                    System.out.println("Não conectado ao servidor de msg");
                }
            }

        }).start();

    }

}
