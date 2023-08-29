package com.example.tictactoe.battleservice.model;

import com.example.tictactoe.battleservice.dto.battle.Result;
import com.example.tictactoe.battleservice.dto.battle.Status;
import com.example.tictactoe.battleservice.dto.battle.board.Symbol;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import java.util.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RedisHash
@NoArgsConstructor
public class BattleModel {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private Status status;
    private Result result;
    @Reference
    private BoardModel board;
    private Map<UUID, Symbol> players;
    private UUID lastPlayedPlayer;

    public BattleModel(UUID firstPlayerId, BoardModel board) {
        this.id = UUID.randomUUID();
        this.players = new HashMap<>(2, 1);
        this.board = board;
        this.status = Status.NEW;
        players.put(firstPlayerId, Symbol.X);
    }

}
