package com.example.tictactoe.battleservice.model;

import com.example.tictactoe.battleservice.dto.battle.board.Symbol;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RedisHash
public class BoardModel {

    private static final String BOARD_STRING_FORMAT = """
                %s|%s|%s
                %s|%s|%s
                %s|%s|%s
                """;
    private static final int SIZE = 3;

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private Symbol[][] board;

    private int numberOfMarkedCells;

    public BoardModel() {
        this.id = UUID.randomUUID();
        this.board = new Symbol[SIZE][SIZE];
    }

    @Override
    public String toString() {
        return BOARD_STRING_FORMAT.formatted(board[0][0], board[0][1], board[0][2],
                board[1][0], board[1][1], board[1][2],
                board[2][0], board[2][1], board[2][2]);
    }
}
