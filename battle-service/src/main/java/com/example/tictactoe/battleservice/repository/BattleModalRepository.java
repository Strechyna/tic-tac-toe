package com.example.tictactoe.battleservice.repository;

import com.example.tictactoe.battleservice.model.BattleModel;
import org.springframework.data.repository.CrudRepository;

public interface BattleModalRepository extends CrudRepository<BattleModel, String> {
}
