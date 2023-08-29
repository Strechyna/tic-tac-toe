package com.example.tictactoe.battleservice.validator.constraint.annotation;

import com.example.tictactoe.battleservice.validator.constraint.PlayerTurnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE  })
@Retention(RUNTIME)
@Constraint(validatedBy = PlayerTurnValidator.class)
@Documented
public @interface PlayerTurn {

    String message() default "It is not your turn. Please wait a bit";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

}
