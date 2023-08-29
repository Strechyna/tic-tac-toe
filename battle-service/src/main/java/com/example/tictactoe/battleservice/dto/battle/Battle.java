package com.example.tictactoe.battleservice.dto.battle;

import com.example.tictactoe.battleservice.dto.battle.board.Board;
import com.example.tictactoe.battleservice.dto.battle.board.Symbol;
import lombok.Getter;

import java.util.*;

import static java.lang.Math.abs;

public class Battle {

    @Getter
    private final UUID id;
    @Getter
    private Status status;
    @Getter
    private Result result;
    @Getter
    private final Board board;
    private final Map<UUID, Symbol> players;
    @Getter
    private UUID lastPlayedPlayer;

    public Battle(UUID firstPlayerId) {
        this.id = UUID.randomUUID();
        this.players = new HashMap<>(2, 1);
        this.board = new Board();
        this.status = Status.NEW;
        players.put(firstPlayerId, Symbol.X);
    }

    public void join(UUID playerId) {
        players.put(playerId, Symbol.O);
        this.status = Status.IN_PROGRESS;
        this.lastPlayedPlayer = playerId;
    }

    public void interrupt() {
        this.status = Status.INTERRUPTED;
    }

    public void makeMove(Move move) {
        board.markCell(move.cell(), players.get(move.playerId()));
        this.lastPlayedPlayer = move.playerId();
        this.result = checkTheWinner();

        if (Objects.nonNull(this.result)) {
            this.status = Status.FINISHED;
        }
    }

    public Set<UUID> getPlayers() {
        return players.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(getId(), ((Battle) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return """
                Battle {
                id: %s,
                status: %s,
                result: %s,
                players: %s,
                lastPlayedPlayer: %s
                board:
                %s}
                """.formatted(id, status, result, players, lastPlayedPlayer, board);
    }

    private Result checkTheWinner() {
        List<Integer> allLines = board.getSumsOfValuesInLines();

        return allLines.stream()
                .filter(sum -> abs(sum) == 3)
                .findFirst()
                .map(sum -> sum == 3 ? Result.FIRST_PLAYER_WON : Result.SECOND_PLAYER_WON)
                .orElseGet(() -> board.isHasEmptyCell() ? null : Result.DRAW);
    }
}
