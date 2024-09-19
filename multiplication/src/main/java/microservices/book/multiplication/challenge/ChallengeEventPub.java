package microservices.book.multiplication.challenge;

import microservices.book.event.challenge.ChallengeSolvedEvent;
import org.apache.rocketmq.client.core.RocketMQClientTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPub {

    private final RocketMQClientTemplate rocketMQClientTemplate;
    private final String topic;

    public ChallengeEventPub(final  RocketMQClientTemplate rocketMQClientTemplate,
                             @Value("${rocketmq.attempts}")
                             final String topic) {
        this.rocketMQClientTemplate = rocketMQClientTemplate;
        this.topic = topic;
    }

    public void challengeSolved(final ChallengeAttempt challengeAttempt) {
        ChallengeSolvedEvent event = buildEvent(challengeAttempt);
        rocketMQClientTemplate.syncSendFifoMessage(topic, event, String.valueOf(event.getUserId()));
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt attempt) {
        return new ChallengeSolvedEvent(attempt.getId(),
                attempt.isCorrect(), attempt.getFactorA(),
                attempt.getFactorB(), attempt.getUser().getId(),
                attempt.getUser().getAlias());
    }
}