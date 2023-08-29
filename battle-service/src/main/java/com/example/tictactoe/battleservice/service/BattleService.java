package com.example.tictactoe.battleservice.service;

import com.example.tictactoe.battleservice.dto.request.Join;
import com.example.tictactoe.battleservice.validator.group.ConstraintGroups;
import com.example.tictactoe.battleservice.dto.request.MakeMove;
import com.example.tictactoe.battleservice.dto.battle.Battle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.UUID;


@Validated(ConstraintGroups.class)
public interface BattleService {

    Collection<Battle> getAllNew();

    Battle get(@NotNull UUID battleId);

    Battle create(@NotNull UUID playerId);

    Battle join(@Valid Join request);

    Battle interrupt(@NotNull UUID battleId);

    Battle makeMove(@Valid MakeMove request);

}
