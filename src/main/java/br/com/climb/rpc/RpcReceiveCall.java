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
import br.com.climb.rpc.annotation.RpcClient;
import br.com.climb.rpc.annotation.RpcController;
import br.com.climb.rpc.exceptions.RpcException;
import br.com.climb.rpc.model.RpcConsumerResponse;
import br.com.climb.rpc.model.RpcProcessedRequest;
import br.com.climb.rpc.request.GetKeyHandler;
import br.com.climb.rpc.request.SendKeyRpc;
import br.com.climb.rpc.request.SendResponseRpc;
import br.com.climb.rpc.request.SendHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.mina.core.RuntimeIoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RpcReceiveCall implements RpcListener {

    private final Logger logger = LoggerFactory.getLogger(RpcReceiveCall.class);

    private final ConfigFile configFile;

    public RpcReceiveCall(ConfigFile configFile) {
        this.configFile = configFile;
    }

    private RpcConsumerResponse getResponse(String chanelName) throws JsonProcessingException, RpcException {

//        System.out.println(String
//                .format("http://%s:%s/v1/rpc/consumer/%s", configFile.getMessageIp(), configFile.getMessagePort(), chanelName));

        final HttpResponse<JsonNode> response = Unirest.get(String
                .format("http://%s:%s/v1/rpc/consumer/%s", configFile.getMessageIp(), configFile.getMessagePort(), chanelName))
                .header("accept", "application/json")
                .asJson();

        System.out.println(response.getBody().toPrettyString());
        return new ObjectMapper().readValue(response.getBody().toPrettyString(), RpcConsumerResponse.class);


    }

    private void invokeMethod(String id, String chanelName, Method method, Object[] args) {

        try(final ManagerContext context = ClimbApplication.containerInitializer.createManager()) {

            final Object instance = context.generateInstance(method.getDeclaringClass());
            final Object result = method.invoke(instance, args);

            final RpcProcessedRequest rpcProcessedRequest = new RpcProcessedRequest();
            rpcProcessedRequest.setId(id);
            rpcProcessedRequest.setChanelName(chanelName);
            rpcProcessedRequest.setResult(new ObjectMapper().writeValueAsString(result));

            System.out.println(new ObjectMapper().writeValueAsString(rpcProcessedRequest));

            HttpResponse<JsonNode> response = Unirest.post(String.format("HTTP://%s:%s/v1/rpc/create/processed", configFile.getMessageIp(), configFile.getMessagePort()))
                    .header("Content-Type", "application/json")
                    .body(new ObjectMapper().writeValueAsString(rpcProcessedRequest))
                    .asJson();

            if (response.getStatus() != 200) {
                throw new RpcException("RPC Error");
            }

        } catch (Exception e) {
            logger.error("invokeMethod {}", e);
        }

    }

    @Override
    public void startListenerCallMethod() throws Exception {

        new Thread(() -> {

            while (true) {

                try {
                    Methods.RPC_CONTROLLERS.forEach(this::accept);
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void accept(String key, Method method) {

        try {

            final RpcController rpcController = method.getDeclaringClass().getDeclaredAnnotation(RpcController.class);

//            System.out.println(rpcController.chanelName());

            final RpcConsumerResponse rpcConsumerResponse = getResponse(rpcController.chanelName());

            if (rpcConsumerResponse.getStatusCode().intValue() == 200) {

                final List<Object> args = rpcConsumerResponse.getArgs().stream().map(arg -> {
                    try {
                        return Class.forName(arg.getType()).getDeclaredConstructor(String.class).newInstance(arg.getValue());
                    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

                invokeMethod(rpcConsumerResponse.getId(), rpcController.chanelName(), method, args.toArray());
            }

        } catch (RpcException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
