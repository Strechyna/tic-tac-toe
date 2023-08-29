package com.example.tictactoe.battleservice.validator.constraint.annotation;

import com.example.tictactoe.battleservice.dto.battle.Status;
import com.example.tictactoe.battleservice.validator.constraint.BattleStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE  })
@Retention(RUNTIME)
@Constraint(validatedBy = BattleStatusValidator.class)
@Documented
public @interface BattleStatus {

    String message() default "Cannot perform this action. The battle status must be {battleStatus}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
    Status targetStatus();

}
