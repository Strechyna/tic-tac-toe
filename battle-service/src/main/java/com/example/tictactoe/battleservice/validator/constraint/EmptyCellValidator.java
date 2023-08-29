package com.example.tictactoe.battleservice.validator.constraint;

import com.example.tictactoe.battleservice.dto.request.MakeMove;
import com.example.tictactoe.battleservice.dto.battle.board.Board;
import com.example.tictactoe.battleservice.dto.battle.board.Cell;
import com.example.tictactoe.battleservice.validator.constraint.annotation.EmptyCell;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmptyCellValidator implements ConstraintValidator<EmptyCell, MakeMove> {

    @Override
    public boolean isValid(MakeMove request, ConstraintValidatorContext context) {
        Cell cell = request.move().cell();
        Board board = request.battle().getBoard();
        return board.isEmptyCell(cell);
    }

}
