package com.example.tictactoe.battleservice.validator.constraint;

import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.dto.battle.Status;
import com.example.tictactoe.battleservice.validator.constraint.annotation.BattleStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

public class BattleStatusValidator implements ConstraintValidator<BattleStatus, Battle> {

    private Status targetStatus;

    @Override
    public void initialize(BattleStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        targetStatus = constraintAnnotation.targetStatus();
    }

    @Override
    public boolean isValid(Battle battle, ConstraintValidatorContext context) {
        boolean isValidStatus = battle.getStatus() == targetStatus;
        if (!isValidStatus) {
            ((ConstraintValidatorContextImpl) context)
                    .addMessageParameter("battleStatus", targetStatus);
        }
        return isValidStatus;
    }

}
