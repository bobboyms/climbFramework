package br.com.climb.framework.messagesclient.restclient.model;

public class TopicProducerDto {

    private final String topicName;
    private final Object topicMessage;

    public TopicProducerDto(String topicName, Object topicMessage) {
        this.topicName = topicName;
        this.topicMessage = topicMessage;
    }

    public String getTopicName() {
        return topicName;
    }

    public Object getTopicMessage() {
        return topicMessage;
    }

    @Override
    public String toString() {
        return "TopicProducerDto{" +
                "topicName='" + topicName + '\'' +
                ", topicMessage=" + topicMessage +
                '}';
    }
}
