package microservices.book.multiplication.challenge;

import microservices.book.event.challenge.ChallengeSolvedEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPub {

    private final KafkaTemplate<String, ChallengeSolvedEvent> kafkaTemplate;
    private final String topic;

    public ChallengeEventPub(final  KafkaTemplate<String,ChallengeSolvedEvent> kafkaTemplate,
                             @Value("${kafka.attempts}")
                             final String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void challengeSolved(final ChallengeAttempt challengeAttempt) {
        ChallengeSolvedEvent event = buildEvent(challengeAttempt);
        ProducerRecord<String, ChallengeSolvedEvent> producerRecord = new ProducerRecord<>(topic,String.valueOf(event.getUserId()), event);
        kafkaTemplate.send(producerRecord);
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt attempt) {
        return new ChallengeSolvedEvent(attempt.getId(),
                attempt.isCorrect(), attempt.getFactorA(),
                attempt.getFactorB(), attempt.getUser().getId(),
                attempt.getUser().getAlias());
    }
}