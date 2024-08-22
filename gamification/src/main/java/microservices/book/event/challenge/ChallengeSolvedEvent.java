package microservices.book.event.challenge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Kafka needs the data model to be in the same package which in different microservice projects (gamification and multiplication)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeSolvedEvent {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;

}

