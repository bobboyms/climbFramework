package br.com.climb.framework.messagesclient;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.framework.messagesclient.restclient.exceptions.CreateTopicException;
import br.com.climb.framework.messagesclient.restclient.model.TopicGetResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageClientSubscribeImp implements MessageClientSubscribe {

    private final Logger logger = LoggerFactory.getLogger(MessageClientSubscribeImp.class);

    private final ConfigFile configFile;
    private final String topicName;

    public MessageClientSubscribeImp(String topicName, ConfigFile configFile) {
        this.topicName = topicName;
        this.configFile = configFile;
    }

    @Override
    public TopicGetResponse getMessage() throws CreateTopicException {

        try {

            final HttpResponse<JsonNode> response = Unirest
                    .get(String.format("http://%s:%s/v1/topic/consumer/%s", configFile.getMessageIp(), configFile.getMessagePort(), topicName))
                    .header("accept", "application/json")
                    .asJson();

            if (response.getStatus() == 500) {
                throw new CreateTopicException("Error get topic. Status code = " + response.getStatus());
            }

            if (response.getStatus() == 200) {
                System.out.println(response.getBody().toPrettyString());
            }

            return new ObjectMapper().
                    readValue(response.getBody().toPrettyString(), TopicGetResponse.class);

        } catch (JsonProcessingException e) {
            logger.error("error {}", e);
            throw new CreateTopicException(e.getMessage());
        }

    }
}
