package br.com.climb.rpc;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.Message;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.framework.exceptions.MethodCallException;
import br.com.climb.rpc.exceptions.RpcException;
import br.com.climb.rpc.model.Arg;
import br.com.climb.rpc.model.RpcProcessedResponse;
import br.com.climb.rpc.model.RpcProducerRequest;
import br.com.climb.rpc.model.RpcProducerResponse;
import br.com.climb.rpc.send.GetHandler;
import br.com.climb.rpc.send.GetRequestRpc;
import br.com.climb.rpc.send.SendRequestRpc;
import br.com.climb.rpc.send.SendtHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RpcSendManager implements RpcMethod {

    private final String chanelName;
    private final String className;
    private final String methodName;
    private final ConfigFile configFile;

    public RpcSendManager(String chanelName, String className, String methodName, ConfigFile configFile) {
        this.chanelName = chanelName;
        this.className = className;
        this.methodName = methodName;
        this.configFile = configFile;
    }

    private String sendMessage(Object[] args) throws RpcException {

        try {

            final List<Arg> argList = new ArrayList<>();

            final RpcProducerRequest rpcProducerRequest = new RpcProducerRequest();
            rpcProducerRequest.setChanelName(chanelName);
            rpcProducerRequest.setClassName(className);
            rpcProducerRequest.setMethodName(methodName);
            rpcProducerRequest.setArgs(argList);

            Arrays.asList(args).forEach(o -> {
                Arg arg = new Arg();
                arg.setName(o.getClass().getName());
                arg.setType(o.getClass().getName());
                arg.setValue(o.toString());
                argList.add(arg);
            });



            final HttpResponse<JsonNode> response = Unirest.post("http://127.0.0.1:3254/v1/rpc/create")
                    .header("Content-Type", "application/json")
                    .body(new ObjectMapper().writeValueAsString(rpcProducerRequest))
                    .asJson();

            final RpcProducerResponse rpcProducerResponse = new ObjectMapper().readValue(response.getBody().toPrettyString(), RpcProducerResponse.class);

            System.out.println("******** RPC CREATE *********");
            System.out.println(new ObjectMapper().writeValueAsString(rpcProducerRequest));
            System.out.println(rpcProducerResponse);

            if (response.getStatus() != 200) {
                throw new RpcException("Error send RPC, " + rpcProducerResponse.getMessage());
            }

            return rpcProducerResponse.getId();

        } catch (JsonProcessingException e) {
            throw new RpcException("Error send RPC, " + e.getMessage());
        }

    }

    private Object waitMessage(String uuid) throws RpcException {

        System.out.println(uuid);

        RpcProcessedResponse rpcProcessedResponse = null;
        int count = 0;
        boolean received = false;
        while (!received) {

            try {
                count++;
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (count == 370000) {
                throw new RpcException("answer exceeded the time limit. Time miles limit = " + 30000);
            }

            try {

                final HttpResponse<JsonNode> response = Unirest.get(String
                        .format("http://127.0.0.1:3254/v1/rpc/get/processed/%s/%s",chanelName,uuid))
                        .header("accept", "application/json")
                        .asJson();

                if (response.getStatus() == 200) {
                    received = true;
                }

                rpcProcessedResponse = new ObjectMapper().readValue(response.getBody().toPrettyString(), RpcProcessedResponse.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

        return rpcProcessedResponse;

    }

    @Override
    public Object methodCall(Object[] args) throws RpcException {
        return waitMessage(sendMessage(args));
    }

}
