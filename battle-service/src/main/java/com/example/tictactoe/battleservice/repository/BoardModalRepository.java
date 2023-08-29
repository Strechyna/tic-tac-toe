package com.example.tictactoe.battleservice.repository;

import com.example.tictactoe.battleservice.model.BoardModel;
import org.springframework.data.repository.CrudRepository;

public interface BoardModalRepository extends CrudRepository<BoardModel, String> {
}
