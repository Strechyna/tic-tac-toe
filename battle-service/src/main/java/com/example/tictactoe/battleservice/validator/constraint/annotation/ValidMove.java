package com.example.tictactoe.battleservice.validator.constraint.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Participant
@PlayerTurn
@EmptyCell
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@Documented
public @interface ValidMove {

    String message() default "The Move record contains wrong values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
