package com.example.tictactoe.battleservice.dto.battle.board;

import lombok.Getter;

import java.util.*;

public class Board {

    private static final String BOARD_STRING_FORMAT = """
                %s|%s|%s
                %s|%s|%s
                %s|%s|%s
                """;
    private static final int SIZE = 3;
    @Getter
    private final Symbol[][] board;
    @Getter
    private int numberOfMarkedCells;

    public Board() {
        this.board = new Symbol[SIZE][SIZE];
    }

    public void markCell(Cell cell, Symbol symbol) {
        if (Objects.nonNull(board[cell.rowIndex()][cell.columnIndex()])) {
            throw new IllegalArgumentException("The cell is not empty. Try another one.");
        }

        board[cell.rowIndex()][cell.columnIndex()] = symbol;
        numberOfMarkedCells++;
    }

    public boolean isHasEmptyCell() {
        return numberOfMarkedCells < SIZE * SIZE;
    }

    public boolean isEmptyCell(Cell cell) {
        return Objects.isNull(board[cell.rowIndex()][cell.columnIndex()]);
    }

    public List<Integer> getSumsOfValuesInLines() {
        if (numberOfMarkedCells < SIZE * 2 -1) {
            return Collections.emptyList();
        }

        ArrayList<Integer> lines = new ArrayList<>(SIZE * 2 + 2);

        int firstDiagonalSum = 0;
        int secondDiagonalSum = 0;
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            int rowSum = 0;
            int columnSum = 0;
            for (int columnIndex = 0; columnIndex < SIZE; columnIndex++) {
                if (Objects.equals(rowIndex, columnIndex)) {
                    firstDiagonalSum += getSymbolValue(board[rowIndex][columnIndex]);
                }
                if (rowIndex + columnIndex == (SIZE - 1)) {
                    secondDiagonalSum += getSymbolValue(board[rowIndex][columnIndex]);
                }
                rowSum += getSymbolValue(board[rowIndex][columnIndex]);
                columnSum += getSymbolValue(board[columnIndex][rowIndex]);
            }
            lines.add(rowSum);
            lines.add(columnSum);
        }
        lines.add(firstDiagonalSum);
        lines.add(secondDiagonalSum);

        return lines;
    }

    @Override
    public String toString() {
        return BOARD_STRING_FORMAT.formatted(board[0][0], board[0][1], board[0][2],
                board[1][0], board[1][1], board[1][2],
                board[2][0], board[2][1], board[2][2]);
    }

    private int getSymbolValue(Symbol symbol) {
        return Optional.ofNullable(symbol)
                .map(Symbol::getValue)
                .orElse(0);
    }
}


