package com.example.tictactoe.battleservice.model.board;

import com.example.tictactoe.battleservice.dto.battle.board.Cell;
import com.example.tictactoe.battleservice.dto.battle.board.Board;
import com.example.tictactoe.battleservice.dto.battle.board.Symbol;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void markCell_cellWithValidIndexes_theCellIsMarkedAndNumberOfMarkedCellsIsIncreased() {
        var board = new Board();
        board.markCell(new Cell(1, 1), Symbol.X);

        assertEquals("""
                null|null|null
                null|X|null
                null|null|null
                """, board.toString());
        assertEquals(1, board.getNumberOfMarkedCells());
    }

    @Test
    void isHasEmptyCell_markedOnlyOneCell_true() {
        var board = new Board();
        board.markCell(new Cell(1, 1), Symbol.X);

        assertTrue(board.isHasEmptyCell());
    }

    @Test
    void isHasEmptyCell_markedAllCells_False() {
        var board = new Board();
        fillBoard(board, Symbol.X);

        assertFalse(board.isHasEmptyCell());
    }

    @Test
    void getSumsOfValuesInLines_allCellsMarkedAsX_listOfThrees() {
        getSumsOfValuesTest(Symbol.X);
    }

    @Test
    void getSumsOfValuesInLines_allCellsMarkedAsO_listOfMinusThrees() {
        getSumsOfValuesTest(Symbol.O);
    }

    @Test
    void getSumsOfValuesInLines_noCellsAreMarked_emptyList() {
        var board = new Board();
        List<Integer> result = board.getSumsOfValuesInLines();

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void toString_allCellsAreMarkedAsX_equalsToExpectedString() {
        toStringTest(Symbol.X, """
                X|X|X
                X|X|X
                X|X|X
                """);
    }

    @Test
    void toString_allCellsAreMarkedAsO_equalsToExpectedString() {
        toStringTest(Symbol.O, """
                O|O|O
                O|O|O
                O|O|O
                """);
    }

    private void getSumsOfValuesTest(Symbol symbol) {
        var board = new Board();
        fillBoard(board, symbol);

        List<Integer> result = board.getSumsOfValuesInLines();

        int sumOfValues = symbol.getValue() * 3;
        assertEquals(List.of(sumOfValues, sumOfValues, sumOfValues, sumOfValues, sumOfValues, sumOfValues,
                sumOfValues, sumOfValues), result);
    }

    private void toStringTest(Symbol symbol, String expected) {
        var board = new Board();
        fillBoard(board, symbol);

        assertEquals(expected, board.toString());
    }

    private void fillBoard(Board board, Symbol symbol) {
        IntStream.range(0, 3)
                .forEach(rowIndex -> IntStream.range(0, 3)
                        .forEach(columnIndex -> board.markCell(new Cell(rowIndex, columnIndex), symbol)));
    }
}