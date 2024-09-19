package microservices.book.multiplication.challenge;

import microservices.book.event.challenge.ChallengeSolvedEvent;
import org.apache.rocketmq.client.core.RocketMQClientTemplate;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import microservices.book.multiplication.user.User;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChallengeEventPubTest {

    private ChallengeEventPub challengeEventPub;

    @Mock
    private RocketMQClientTemplate rocketMQClientTemplate;

    @BeforeEach
    public void setUp() {
        challengeEventPub = new ChallengeEventPub(rocketMQClientTemplate,
                "test-topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void sendsAttempt(boolean correct) {
        // given
        ChallengeAttempt attempt = createTestAttempt(correct);

        // when
        challengeEventPub.challengeSolved(attempt);

        // then
        var topicCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);
        var keyCaptor = ArgumentCaptor.forClass(String.class);


        verify(rocketMQClientTemplate).syncSendFifoMessage(topicCaptor.capture(), eventCaptor.capture(), keyCaptor.capture());
        then(topicCaptor.getValue()).isEqualTo("test-topic");
        then(eventCaptor.getValue()).isEqualTo(solvedEvent(correct));
    }

    private ChallengeAttempt createTestAttempt(boolean correct) {
        return new ChallengeAttempt(1L, new User(10L, "john"), 30, 40,
                correct ? 1200 : 1300, correct);
    }

    private ChallengeSolvedEvent solvedEvent(boolean correct) {
        return new ChallengeSolvedEvent(1L, correct, 30, 40, 10L, "john");
    }

}