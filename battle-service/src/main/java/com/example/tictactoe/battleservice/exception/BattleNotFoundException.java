package com.example.tictactoe.battleservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BattleNotFoundException extends RuntimeException {

    public BattleNotFoundException(UUID battleId) {
        super("The battle with battleId %s doesn't exist".formatted(battleId));
    }
}
