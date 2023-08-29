package com.example.tictactoe.battleservice.model.battle;

import com.example.tictactoe.battleservice.dto.battle.board.Cell;
import com.example.tictactoe.battleservice.dto.battle.Move;
import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.dto.battle.Result;
import com.example.tictactoe.battleservice.dto.battle.Status;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void create_battleWithDefaultValuesCreated() {
        UUID firstPlayerUUID = UUID.randomUUID();
        Battle battle = new Battle(firstPlayerUUID);

        assertNotNull(battle.getId());
        assertEquals(Status.NEW, battle.getStatus());
        assertNull(battle.getResult());
        assertEquals(1, battle.getPlayers().size());
        assertTrue(battle.getPlayers().contains(firstPlayerUUID));
    }

    @Test
    void join_thePlayerJoinedToTheBattle() {
        UUID firstPlayerUUID = UUID.randomUUID();
        Battle battle = new Battle(firstPlayerUUID);

        UUID secondPlayerUUID = UUID.randomUUID();
        battle.join(secondPlayerUUID);

        assertEquals(Status.IN_PROGRESS, battle.getStatus());
        assertNull(battle.getResult());
        assertEquals(2, battle.getPlayers().size());
        assertTrue(battle.getPlayers().contains(firstPlayerUUID));
        assertTrue(battle.getPlayers().contains(secondPlayerUUID));
        assertEquals(secondPlayerUUID, battle.getLastPlayedPlayer());
    }

    @Test
    void makeMove_oneMove_statusIsInProgressAndResultIsNull() {
        UUID firstPlayerUUID = UUID.randomUUID();
        Battle battle = new Battle(firstPlayerUUID);

        UUID secondPlayerUUID = UUID.randomUUID();
        battle.join(secondPlayerUUID);

        battle.makeMove(new Move(firstPlayerUUID, new Cell(1, 1)));

        assertEquals(Status.IN_PROGRESS, battle.getStatus());
        assertNull(battle.getResult());
    }

    @Test
    void makeMove_firstPlayerFillTheLine_statusIsFinishedAndResultIsFirstPlayerWon() {
        UUID firstPlayerUUID = UUID.randomUUID();
        Battle battle = new Battle(firstPlayerUUID);

        UUID secondPlayerUUID = UUID.randomUUID();
        battle.join(secondPlayerUUID);

        battle.makeMove(new Move(firstPlayerUUID, new Cell(0, 0)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(1, 0)));
        battle.makeMove(new Move(firstPlayerUUID, new Cell(0, 1)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(1, 1)));
        battle.makeMove(new Move(firstPlayerUUID, new Cell(0, 2)));

        assertEquals(Status.FINISHED, battle.getStatus());
        assertEquals(Result.FIRST_PLAYER_WON, battle.getResult());
    }

    @Test
    void makeMove_secondPlayerFillTheLine_statusIsFinishedAndResultIsSecondPlayerWon() {
        UUID firstPlayerUUID = UUID.randomUUID();
        Battle battle = new Battle(firstPlayerUUID);

        UUID secondPlayerUUID = UUID.randomUUID();
        battle.join(secondPlayerUUID);

        battle.makeMove(new Move(firstPlayerUUID, new Cell(0, 0)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(1, 0)));
        battle.makeMove(new Move(firstPlayerUUID, new Cell(0, 1)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(1, 1)));
        battle.makeMove(new Move(firstPlayerUUID, new Cell(2, 2)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(1, 2)));

        assertEquals(Status.FINISHED, battle.getStatus());
        assertEquals(Result.SECOND_PLAYER_WON, battle.getResult());
    }

    @Test
    void makeMove_draw_statusIsFinishedAndResultIsDraw() {
        UUID firstPlayerUUID = UUID.randomUUID();
        Battle battle = new Battle(firstPlayerUUID);

        UUID secondPlayerUUID = UUID.randomUUID();
        battle.join(secondPlayerUUID);

        battle.makeMove(new Move(firstPlayerUUID, new Cell(0, 0)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(0, 1)));
        battle.makeMove(new Move(firstPlayerUUID, new Cell(0, 2)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(1, 0)));
        battle.makeMove(new Move(firstPlayerUUID, new Cell(1, 1)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(1, 2)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(2, 0)));
        battle.makeMove(new Move(firstPlayerUUID, new Cell(2, 1)));
        battle.makeMove(new Move(secondPlayerUUID, new Cell(2, 2)));

        assertEquals(Status.FINISHED, battle.getStatus());
        assertEquals(Result.DRAW, battle.getResult());
    }
}