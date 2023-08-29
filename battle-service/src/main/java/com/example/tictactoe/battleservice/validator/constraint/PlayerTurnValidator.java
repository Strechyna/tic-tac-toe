package com.example.tictactoe.battleservice.validator.constraint;

import com.example.tictactoe.battleservice.dto.request.MakeMove;
import com.example.tictactoe.battleservice.validator.constraint.annotation.PlayerTurn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.UUID;

public class PlayerTurnValidator implements ConstraintValidator<PlayerTurn, MakeMove> {

    @Override
    public boolean isValid(MakeMove request, ConstraintValidatorContext context) {
        UUID playerId = request.move().playerId();
        return !Objects.equals(request.battle().getLastPlayedPlayer(), playerId);
    }

}
