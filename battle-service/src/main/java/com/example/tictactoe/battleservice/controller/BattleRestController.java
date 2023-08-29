package com.example.tictactoe.battleservice.controller;

import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.model.BattleModel;
import com.example.tictactoe.battleservice.model.BoardModel;
import com.example.tictactoe.battleservice.repository.BattleModalRepository;
import com.example.tictactoe.battleservice.repository.BoardModalRepository;
import com.example.tictactoe.battleservice.service.BattleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Validated
@RestController()
@RequestMapping("battles")
@RequiredArgsConstructor
public class BattleRestController {

    private final BattleService service;
    private final BoardModalRepository boardModalRepository;
    private final BattleModalRepository battleModalRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String STRING_KEY_PREFIX = "test:strings:";

    @GetMapping("echo")
    public String echo() {
        return "I am alive";
    }

    @GetMapping
    public Collection<Battle> getAllNew() {
        return service.getAllNew();
    }

    @GetMapping("{battleId}")
    public Battle get(@PathVariable @NotNull UUID battleId) {
        return service.get(battleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Battle create(@RequestBody @NotNull @org.hibernate.validator.constraints.UUID String playerId) {
        return service.create(UUID.fromString(playerId));
    }

    @PostMapping("redis")
    @ResponseStatus(HttpStatus.CREATED)
    public void setString() {
        BoardModel boardModel = boardModalRepository.save(new BoardModel());
        battleModalRepository.save(new BattleModel(UUID.randomUUID(), boardModel));
    }

    @GetMapping("redis/{key}")
    public BattleModel getString(@PathVariable("key") String key) {
        return battleModalRepository.findById(key).get();
    }

}
