package com.example.tictactoe.battleservice.validator.constraint;

import com.example.tictactoe.battleservice.dto.request.MakeMove;
import com.example.tictactoe.battleservice.validator.constraint.annotation.Participant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import java.util.UUID;

public class ParticipantValidator implements ConstraintValidator<Participant, MakeMove> {

    @Override
    public boolean isValid(MakeMove request, ConstraintValidatorContext context) {
        UUID playerId = request.move().playerId();
        boolean isParticipant = request.battle().getPlayers().contains(playerId);
        if (!isParticipant) {
            ((ConstraintValidatorContextImpl) context)
                    .addMessageParameter("battleId", request.battle().getId());
        }

        return isParticipant;
    }

}
