package br.com.climb.framework.messagesclient;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.framework.messagesclient.restclient.exceptions.CreateTopicException;
import br.com.climb.framework.messagesclient.restclient.model.TopicProducerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageClientProducerImp implements MessageClientProducer {

    private final Logger logger = LoggerFactory.getLogger(MessageClientProducerImp.class);

    private final ConfigFile configFile;
    private final String topicName;

    public MessageClientProducerImp(String topicName, ConfigFile configFile) {
        this.topicName = topicName;
        this.configFile = configFile;
    }

    @Override
    public void sendMessage(Object message) throws CreateTopicException {

        try {

            final HttpResponse<JsonNode> response = Unirest.post(String.format("http://%s:%s/v1/topic/producer", configFile.getMessageIp(), configFile.getMessagePort()))
                    .header("Content-Type", "application/json")
                    .body(new TopicProducerDto(topicName, new ObjectMapper().writeValueAsString(message)))
                    .asJson();

            if (response.getStatus() != 200) {
                throw new CreateTopicException("Not create topic. Status code = " + response.getStatus());
            }

        } catch (JsonProcessingException e) {
            logger.error("error {}", e);
            throw new CreateTopicException(e.getMessage());
        }


    }
}
