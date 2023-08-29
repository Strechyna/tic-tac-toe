package com.example.tictactoe.battleservice.service;

import com.example.tictactoe.battleservice.dto.request.Join;
import com.example.tictactoe.battleservice.exception.BattleNotFoundException;
import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.dto.battle.Move;
import com.example.tictactoe.battleservice.dto.battle.board.Cell;
import com.example.tictactoe.battleservice.dto.battle.board.Symbol;
import com.example.tictactoe.battleservice.repository.BattleRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

@SpringBootTest
class BattleServiceTest {

    @MockBean
    BattleRepository repository;

    @Autowired
    BattleService battleService;

    private static UUID firstPlayerId;
    private static UUID secondPlayerId;

    @BeforeAll
    static void initialize() {
        firstPlayerId = UUID.randomUUID();
        secondPlayerId = UUID.randomUUID();
    }

    @Nested
    @DisplayName("getBattle tests")
    class GetBattle {

        private static Battle defaultBattle;

        @BeforeAll
        static void initialize() {
            defaultBattle = new Battle(firstPlayerId);
        }

        @Test
        void nullBattleId_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class, () -> battleService.get(null), "get.battleId: must not be null");
        }

        @Test
        void battleNotExist_throwsConstraintViolationException() {
            when(repository.getById(any())).thenReturn(null);

            assertThrowsExceptionWithMessage(BattleNotFoundException.class, () -> battleService.get(defaultBattle.getId()), "The battle with battleId %s doesn't exist".formatted(defaultBattle.getId()));
        }

        @Test
        void battleExists_returnBattle() {
            when(repository.getById(defaultBattle.getId())).thenReturn(defaultBattle);

            Battle result = battleService.get(defaultBattle.getId());

            assertNotNull(result);
            assertEquals(defaultBattle, result);
        }
    }

    @Nested
    @DisplayName("createBattle tests")
    class CreateBattle {

        @Test
        void notNullPlayerId_battleCreated() {
            when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

            Battle result = battleService.create(firstPlayerId);
            assertNotNull(result);

            verify(repository).save(any());
        }

        @Test
        void nullPlayerId_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class, () -> battleService.create(null), "create.playerId: must not be null");
        }
    }

    @Nested
    @DisplayName("joinToBattle tests")
    class JoinToBattle {

        private static Battle defaultBattle;

        @BeforeAll
        static void initialize() {
            defaultBattle = new Battle(firstPlayerId);
        }

//        @Test
//        void notNullBattleAndNotNullPlayerId_secondPlayerJoinedBattle() {
//            Battle battle = new Battle(firstPlayerId);
//            battleService.join(new BattleService.JoinRequest(battle, secondPlayerId));
//
//            assertEquals(Status.IN_PROGRESS, battle.getStatus());
//            assertEquals(2, battle.getPlayers().size());
//            assertNotNull(battle.getLastPlayedPlayer());
//        }

        @Test
        void battleIsNull_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class, () -> battleService.join(new Join(null, UUID.randomUUID())), "join.request.battle: must not be null");
        }

//        @Test
//        void notExistingBattleIdAndNotNullPlayerId_throwsIllegalArgumentException() {
//            assertThrowsExceptionWithMessage(IllegalArgumentException.class, () -> battleService.join(new BattleService.JoinRequest(defaultBattle, UUID.randomUUID())), "The battle with battleId %s doesn't exist".formatted(defaultBattle.getId()));
//        }

        @Test
        void playerIdIsNull_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class, () -> battleService.join(new Join(defaultBattle, null)), "join.request.playerId: must not be null");
        }

        @Test
        void battleWithStatusInProgress_throwsConstraintViolationException() {
            Battle battle = new Battle(firstPlayerId);
            battle.join(secondPlayerId);
            assertThrowsExceptionWithMessage(ConstraintViolationException.class, () -> battleService.join(new Join(battle, secondPlayerId)), "join.request.battle: Cannot perform this action. The battle status must be NEW");
        }
    }

    @Nested
    @DisplayName("makeMove tests")
    class MakeMove {

        private static Battle defaultBattle;

        @BeforeAll
        static void initialize() {
            defaultBattle = new Battle(firstPlayerId);
            defaultBattle.join(secondPlayerId);
        }

        @Test
        //TODO
        void validRequest_moveWasMadeSuccessfully() {
            Battle battle = new Battle(firstPlayerId);
            battle.join(secondPlayerId);

            battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(battle, new Move(firstPlayerId, new Cell(0, 0))));
            //assertNull(battle.getResult());
        }

        @Test
        void nullBattle_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(null, new Move(firstPlayerId, new Cell(0, 0)))),
                    "makeMove.request.battle: must not be null");
        }

//        @Test
//        void makeMove_notExistingBattleId_throwsConstraintViolationException() {
//            UUID battleId = UUID.randomUUID();
//            assertThrowsExceptionWithMessage(IllegalArgumentException.class,
//                    () -> battleService.makeMove(battleId, UUID.randomUUID(), new Cell(0, 0)),
//                    "The battle with battleId %s doesn't exist".formatted(battleId));
//        }

        @Test
        void nullPlayerId_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(defaultBattle, new Move(null, new Cell(0, 0)))),
                    "makeMove.request.move.playerId: must not be null");
        }

        @Test
        void nullCell_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(defaultBattle, new Move(firstPlayerId, null))),
                    "makeMove.request.move.cell: must not be null");
        }

        @Test
        void cellWithInvalidRowIndexes_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(defaultBattle, new Move(firstPlayerId, new Cell(8, 0)))),
                    "makeMove.request.move.cell.rowIndex: must be less than or equal to 2");
        }

        @Test
        void cellWithInvalidColumnIndexes_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(defaultBattle, new Move(firstPlayerId, new Cell(0, 8)))),
                    "makeMove.request.move.cell.columnIndex: must be less than or equal to 2");
        }

        @Test
        void battleWithStatusNew_throwsConstraintViolationException() {
            Battle battle = new Battle(firstPlayerId);

            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(battle, new Move(firstPlayerId, new Cell(0, 0)))),
                    "makeMove.request.battle: Cannot perform this action. The battle status must be IN_PROGRESS");
        }

        @Test
        void moveWasMadeByNotParticipant_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(defaultBattle, new Move(UUID.randomUUID(), new Cell(0, 0)))),
                    "makeMove.request: The battle with battleId '%s' is not yours".formatted(defaultBattle.getId()));
        }

        @Test
        void itIsNotPlayerTurn_throwsConstraintViolationException() {
            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(defaultBattle, new Move(secondPlayerId, new Cell(0, 0)))),
                    "makeMove.request: It is not your turn. Please wait a bit");
        }

        @Test
        void notEmptyCell_throwsConstraintViolationException() {
            Battle battle = new Battle(firstPlayerId);
            battle.join(secondPlayerId);
            Cell cell = new Cell(0, 0);
            battle.getBoard().markCell(cell, Symbol.X);

            assertThrowsExceptionWithMessage(ConstraintViolationException.class,
                    () -> battleService.makeMove(new com.example.tictactoe.battleservice.dto.request.MakeMove(battle, new Move(firstPlayerId, cell))),
                    "makeMove.request: The cell is not empty, please try another cell");
        }
    }

    private void assertThrowsExceptionWithMessage(Class<? extends Exception> expectedExceptionType, Executable executable, String expectedExceptionMessage) {
        Exception exception = assertThrows(expectedExceptionType, executable);
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }
}