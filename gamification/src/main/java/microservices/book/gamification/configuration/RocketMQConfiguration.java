package microservices.book.gamification.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfiguration {
/*    @Bean
    public KafkaAdmin.NewTopics topic(@Value("${kafka.attempts}") String topic,
                                      @Value("${kafka.attempts.partitions}") Integer partitions,
                                      @Value("${kafka.attempts.replica-count}") Integer replicaCount) {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(topic)
                        .partitions(partitions)
                        .replicas(replicaCount).build());
    }*/
}
