package br.com.climb.rpc;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.Message;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.framework.ClimbApplication;
import br.com.climb.framework.messagesclient.Methods;
import br.com.climb.rpc.request.GetKeyHandler;
import br.com.climb.rpc.request.SendKeyRpc;
import br.com.climb.rpc.request.SendResponseRpc;
import br.com.climb.rpc.request.SendHandler;
import org.apache.mina.core.RuntimeIoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RpcReceiveCall implements RpcListener {

    public RpcReceiveCall(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public static final Logger logger = LoggerFactory.getLogger(RpcReceiveCall.class);

    private final ConfigFile configFile;

    private Object getResponseList() {
        final List<String> methodsName = Methods.RPC_CONTROLLERS.entrySet()
                .stream().map(Map.Entry::getKey).collect(Collectors.toList());

        final TcpClient discoveryClient = new SendKeyRpc(new GetKeyHandler(), configFile.getMessageIp(),new Integer(configFile.getMessagePort()));
        discoveryClient.sendRequest(new KeyRpc("", KeyRpc.TYPE_GET_RESPONSE_LIST, Message.TYPE_RPC, methodsName));
        Object response = discoveryClient.getResponse();
        discoveryClient.closeConnection();

        return response;
    }

    private void invokeMethod(Method method, RpcRequest rpcRequest) {

        if (method != null) {

            try(final ManagerContext context = ClimbApplication.containerInitializer.createManager()) {

                final Object instance = context.generateInstance(method.getDeclaringClass());
                final Object result = method.invoke(instance, rpcRequest.getArgs());

                final TcpClient resp = new SendResponseRpc(new SendHandler(), configFile.getMessageIp(),new Integer(configFile.getMessagePort()));
                resp.sendRequest(new RpcResponse(rpcRequest.getUuid(), 200, Message.TYPE_RPC ,result));
                resp.closeConnection();

            } catch (Exception e) {
                logger.error("invokeMethod {}", e);
            }

        }
    }

    @Override
    public void startListenerCallMethod() throws Exception {

        new Thread(() -> {

            while (true) {

                try {

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final Object response = getResponseList();

                    if (response.getClass() == Integer.class) {
                        continue;
                    }

                    final List<RpcRequest> rpcRequests = (List<RpcRequest>)response;

                    rpcRequests.forEach(rpcRequest -> {
                        Method method = Methods.RPC_CONTROLLERS.get(rpcRequest.getMethodName());
                        invokeMethod(method, rpcRequest);
                    });

                } catch (RuntimeIoException e) {
//                    System.out.println("Não conectado ao servidor de msg");
                } catch (Exception e) {
                    logger.error("startListenerCallMethod {}", e);
                }
            }

        }).start();

    }

}
