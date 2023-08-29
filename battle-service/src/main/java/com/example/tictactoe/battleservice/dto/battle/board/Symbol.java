package com.example.tictactoe.battleservice.dto.battle.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Symbol {
    X(1), O(-1);

    private final int value;
}
