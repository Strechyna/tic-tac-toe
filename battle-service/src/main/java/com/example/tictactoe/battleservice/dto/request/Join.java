package com.example.tictactoe.battleservice.dto.request;

import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.dto.battle.Status;
import com.example.tictactoe.battleservice.validator.constraint.annotation.BattleStatus;
import com.example.tictactoe.battleservice.validator.group.ConsistentData;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record Join(@NotNull
                   @BattleStatus(targetStatus = Status.NEW, groups = ConsistentData.class) Battle battle,
                   @NotNull UUID playerId) {
}
