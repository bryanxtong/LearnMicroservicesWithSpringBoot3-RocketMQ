package microservices.book.gamification.game;

import microservices.book.event.challenge.ChallengeSolvedEvent;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
@RocketMQMessageListener(nameServer ="localhost:9876",consumerGroup = "gamification", topic = "${rocketmq.attempts}", selectorType = SelectorType.TAG )
public class GameEventHandler implements RocketMQListener<ChallengeSolvedEvent> {

    private final GameService gameService;

    public void onMessage(final ChallengeSolvedEvent event) {
        log.info("Challenge Solved Event received: {}", event.getAttemptId());
        try {
            gameService.newAttemptForUser(event);
        } catch (final Exception e) {
            log.error("Error when trying to process ChallengeSolvedEvent", e);
        }
    }

}