package microservices.book.multiplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configures Kafka to use events in our application.
 */
@Configuration
public class RocketMQConfiguration {

    /*@Bean
    public RocketMQAdmin.NewTopics topic(@Value("${rocketmq.attempts}") String topic,
                                      @Value("${rocketmq.attempts.partitions}") Integer partitions,
                                      @Value("${rocketmq.attempts.replica-count}") Integer replicaCount) {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(topic)
                        .partitions(partitions)
                        .replicas(replicaCount).build());
    }*/
}