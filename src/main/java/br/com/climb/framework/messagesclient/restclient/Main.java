package br.com.climb.framework.messagesclient.restclient;


import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.framework.ClimbApplication;
import br.com.climb.framework.messagesclient.restclient.model.TopicProducerDto;
import br.com.climb.rpc.RpcMethod;
import br.com.climb.rpc.RpcSendManager;
import br.com.climb.rpc.annotation.RpcClient;
import br.com.climb.rpc.exceptions.RpcException;
import br.com.climb.rpc.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public void soma(Integer a, Float b) {

    }

    public static void rpcProducer() throws JsonProcessingException {
        final String chanelName = "chanelTeste";

        RpcProducerRequest rpcProducerRequest = new RpcProducerRequest();
        rpcProducerRequest.setChanelName(chanelName);
        rpcProducerRequest.setMethodName("methodTeste");
        rpcProducerRequest.setClassName("classTeste");

        Arg arg = new Arg();
        arg.setName("a");
        arg.setType("b");
        arg.setValue("c");

        List<Arg> args1 = new ArrayList<>();
        args1.add(arg);

        rpcProducerRequest.setArgs(args1);

        HttpResponse<JsonNode> response1 = Unirest.post("http://127.0.0.1:3254/v1/rpc/create")
                .header("Content-Type", "application/json")
                .body(new ObjectMapper().writeValueAsString(rpcProducerRequest))
                .asJson();


        System.out.println(response1.getStatus());
        RpcProducerResponse rpcProducerResponse = new ObjectMapper().readValue(response1.getBody().toPrettyString(), RpcProducerResponse.class);

        System.out.println("************* RPC CREATE ***************");
        System.out.println(rpcProducerResponse);
    }

    public static void rpcProcessed() throws JsonProcessingException {

        System.out.println("************* CONSUMER ***************");
        final String chanelName = "chanelTeste";

        HttpResponse<JsonNode> response1 = Unirest.get(String
                .format("http://127.0.0.1:3254/v1/rpc/consumer/%s",chanelName))
                .header("accept", "application/json")
                .asJson();

//        System.out.println(response1.getBody().toPrettyString());

        RpcConsumerResponse rpcConsumerResponse = new ObjectMapper().readValue(response1.getBody().toPrettyString(), br.com.climb.rpc.model.RpcConsumerResponse.class);
        System.out.println(rpcConsumerResponse);

        System.out.println("*************************************");
        System.out.println(rpcConsumerResponse.getId());

        RpcProcessedRequest rpcProcessedRequest = new RpcProcessedRequest();
        rpcProcessedRequest.setId(rpcConsumerResponse.getId());
        rpcProcessedRequest.setChanelName(chanelName);
        rpcProcessedRequest.setResult("resultado AAA");

        HttpResponse<JsonNode> response2 = Unirest.post("HTTP://127.0.0.1:3254/v1/rpc/create/processed")
                .header("Content-Type", "application/json")
                .body(new ObjectMapper().writeValueAsString(rpcProcessedRequest))
                .asJson();

        System.out.println(rpcProcessedRequest);
        System.out.println(response2.getStatus());
        System.out.println(rpcConsumerResponse.getId());

        System.out.println("*************************************");

        HttpResponse<JsonNode> response3 = Unirest.get(String
                .format("http://127.0.0.1:3254/v1/rpc/get/processed/%s/%s","chanelteste",rpcConsumerResponse.getId()))
                .header("accept", "application/json")
                .asJson();

        RpcProcessedResponse rpcProcessedResponse = new ObjectMapper().readValue(response3.getBody().toPrettyString(), RpcProcessedResponse.class);
        System.out.println(rpcProcessedResponse);



    }

    public static void rpcSend() throws RpcException {
//        final RpcClient rpcClient = ctx.getMethod().getDeclaredAnnotation(RpcClient.class);

        List<Object> objects = new ArrayList<>();
        objects.add(new Integer("12"));
        objects.add(new Integer("1254"));

        final RpcMethod rpcMethod = new RpcSendManager("estoqueRpc", "RpcClientController", "somar", ClimbApplication.configFile);
        final RpcProcessedResponse object = (RpcProcessedResponse) rpcMethod.methodCall(objects.toArray());

        System.out.println(object);

    }

    public static void main(String[] args) throws JsonProcessingException, NoSuchMethodException, RpcException {

        rpcSend();

        //
//        rpcProducer();
//        rpcProcessed();
    }
}
