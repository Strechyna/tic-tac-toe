package com.example.tictactoe.battleservice.validator.constraint.annotation;

import com.example.tictactoe.battleservice.validator.constraint.ParticipantValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE  })
@Retention(RUNTIME)
@Constraint(validatedBy = ParticipantValidator.class)
@Documented
public @interface Participant {

    String message() default "The battle with battleId '{battleId}' is not yours";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

}
