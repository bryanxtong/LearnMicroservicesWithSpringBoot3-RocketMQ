package microservices.book.multiplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
/**
 * Configures Kafka to use events in our application.
 */
@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaAdmin.NewTopics topic(@Value("${kafka.attempts}") String topic,
                                      @Value("${kafka.attempts.partitions}") Integer partitions,
                                      @Value("${kafka.attempts.replica-count}") Integer replicaCount) {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(topic)
                        .partitions(partitions)
                        .replicas(replicaCount).build());
    }
}