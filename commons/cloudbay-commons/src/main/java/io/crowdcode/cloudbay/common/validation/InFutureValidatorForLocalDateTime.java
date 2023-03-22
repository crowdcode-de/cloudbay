package io.crowdcode.cloudbay.common.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalDateTime;

import static io.crowdcode.cloudbay.common.AnsiColor.green;
import static io.crowdcode.cloudbay.common.AnsiColor.yellow;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class InFutureValidatorForLocalDateTime implements ConstraintValidator<InFuture, LocalDateTime> {

    private static final Logger log = LoggerFactory.getLogger(InFutureValidatorForLocalDateTime.class);

    private Duration duration;

    @Override
    public void initialize(InFuture constraintAnnotation) {
        log.info(yellow("Initialize with {}. "), constraintAnnotation);
        duration = Duration.of(constraintAnnotation.amount(), constraintAnnotation.chronoUnit());
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        log.info(green("validating {} against {}."), value, duration);
        return (value != null) && LocalDateTime.now().plus(duration).isBefore(value);
    }

}
