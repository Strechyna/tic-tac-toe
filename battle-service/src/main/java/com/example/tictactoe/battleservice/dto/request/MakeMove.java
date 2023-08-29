package com.example.tictactoe.battleservice.dto.request;

import com.example.tictactoe.battleservice.dto.battle.Move;
import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.dto.battle.Status;
import com.example.tictactoe.battleservice.validator.constraint.annotation.BattleStatus;
import com.example.tictactoe.battleservice.validator.constraint.annotation.ValidMove;
import com.example.tictactoe.battleservice.validator.group.ConsistentData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@ValidMove(groups = ConsistentData.class)
public record MakeMove(@NotNull
                       @BattleStatus(targetStatus = Status.IN_PROGRESS, groups = ConsistentData.class) Battle battle,
                       @Valid Move move) {
}
