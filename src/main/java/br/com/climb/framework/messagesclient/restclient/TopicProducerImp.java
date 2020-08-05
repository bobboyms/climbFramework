package br.com.climb.framework.messagesclient.restclient;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.framework.messagesclient.MessageClientProducer;
import br.com.climb.framework.messagesclient.restclient.exceptions.CreateTopicException;
import br.com.climb.framework.messagesclient.restclient.model.TopicProducerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class TopicProducerImp implements MessageClientProducer {

    private final ConfigFile configFile;
    private final String topicName;

    public TopicProducerImp(String topicName, ConfigFile configFile) {
        this.topicName = topicName;
        this.configFile = configFile;
    }

    @Override
    public void sendMessage(Object message) throws CreateTopicException {

        try {
            final String messageStr = new ObjectMapper().writeValueAsString(message);
            final HttpResponse<JsonNode> response = Unirest.post(String.format("http://%s:%s/v1/topic/producer", configFile.getMessageIp(), configFile.getMessagePort()))
                    .header("Content-Type", "application/json")
                    .body(new TopicProducerDto(topicName, messageStr))
                    .asJson();

            if (response.getStatus() != 200) {
                throw new CreateTopicException("Not create topic. Status code = " + response.getStatus());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

}
