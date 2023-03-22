package io.crowdcode.cloudbay.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

/**
 * @author Ingo Düppe (Crowdcode)
 */

@Documented
@Constraint(validatedBy = InFutureValidatorForLocalDateTime.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InFuture {

    long amount() default 1;

    ChronoUnit chronoUnit() default ChronoUnit.HOURS;

    String message() default "{de.crowdcode.cloudbay.commons.validator.InFuture.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
