package microservices.book.multiplication.challenge;

import microservices.book.event.challenge.ChallengeSolvedEvent;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPub {

    private final RocketMQTemplate rocketMQTemplate;
    private final String topic;

    public ChallengeEventPub(final  RocketMQTemplate rocketMQTemplate,
                             @Value("${rocketmq.attempts}")
                             final String topic) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.topic = topic;
    }

    public void challengeSolved(final ChallengeAttempt challengeAttempt) {
        ChallengeSolvedEvent event = buildEvent(challengeAttempt);
/*
        ProducerRecord<String, ChallengeSolvedEvent> producerRecord = new ProducerRecord<>(topic,String.valueOf(event.getUserId()), event);
*/
        rocketMQTemplate.syncSend(topic, event, event.getUserId());
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt attempt) {
        return new ChallengeSolvedEvent(attempt.getId(),
                attempt.isCorrect(), attempt.getFactorA(),
                attempt.getFactorB(), attempt.getUser().getId(),
                attempt.getUser().getAlias());
    }
}