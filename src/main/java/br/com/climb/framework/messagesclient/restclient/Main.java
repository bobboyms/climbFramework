package br.com.climb.framework.messagesclient.restclient;


import br.com.climb.framework.messagesclient.restclient.model.TopicGetResponse;
import br.com.climb.framework.messagesclient.restclient.model.TopicProducerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        HttpResponse<JsonNode> response1 = Unirest.post("http://127.0.0.1:3254/v1/topic/producer")
                .header("Content-Type", "application/json")
                .body(new TopicProducerDto("teste", "message"))
                .asJson();

        System.out.println(response1.getBody().toPrettyString());

        HttpResponse<JsonNode> response2 = Unirest.get("http://127.0.0.1:3254/v1/topic/consumer/teste")
                .header("accept", "application/json")
                .asJson();

        TopicGetResponse topicGet = new ObjectMapper().readValue(response2.getBody().toPrettyString(), TopicGetResponse.class);
        System.out.println(topicGet);

    }
}
