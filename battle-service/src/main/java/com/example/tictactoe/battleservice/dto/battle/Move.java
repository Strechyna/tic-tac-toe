package com.example.tictactoe.battleservice.dto.battle;

import com.example.tictactoe.battleservice.dto.battle.board.Cell;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record Move(@NotNull UUID playerId,
                   @NotNull @Valid Cell cell) {
}
