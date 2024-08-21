package microservices.book.multiplication.challenge;

import jakarta.validation.constraints.*;
import lombok.Value;

/**
 * Attempt coming from the user
 */
@Value
public class ChallengeAttemptDTO {

    @Min(1) @Max(99)
    int factorA, factorB;
    @NotBlank
    String alias;
    @Positive(message = "How could you possibly get a negative result here? Try again.")
    int guess;
}
