package com.example.tictactoe.battleservice.dto.battle.board;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record Cell(@Min(value = 0)
                   @Max(value = 2) int rowIndex,
                   @Min(value = 0)
                   @Max(value = 2) int columnIndex) {
}
