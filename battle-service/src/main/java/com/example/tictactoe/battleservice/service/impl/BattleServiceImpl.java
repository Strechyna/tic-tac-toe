package com.example.tictactoe.battleservice.service.impl;

import com.example.tictactoe.battleservice.dto.request.Join;
import com.example.tictactoe.battleservice.dto.request.MakeMove;
import com.example.tictactoe.battleservice.exception.BattleNotFoundException;
import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.repository.BattleRepository;
import com.example.tictactoe.battleservice.service.BattleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final BattleRepository repository;

    @Override
    public Collection<Battle> getAllNew() {
        return repository.findAll();
    }

    @Override
    public Battle get(@NotNull UUID battleId) {
        return Optional.ofNullable(repository.getById(battleId))
                .orElseThrow(() -> new BattleNotFoundException(battleId));
    }

    @Override
    public Battle create(@NotNull UUID playerId) {
        return repository.save(new Battle(playerId));
    }

    @Override
    public Battle join(Join request) {
        return repository.update(request.battle().getId(), (battle -> battle.join(request.playerId())));
    }

    @Override
    public Battle interrupt(@NotNull UUID battleId) {
        return repository.update(battleId, (Battle::interrupt));
    }

    @Override
    public Battle makeMove(MakeMove request) {
        return repository.update(request.battle().getId(), (battle -> battle.makeMove(request.move())));
    }
}
