package com.example.tictactoe.battleservice.repository;

import com.example.tictactoe.battleservice.dto.battle.Battle;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class BattleRepository {

    private final ConcurrentHashMap<UUID, Battle> map;

    public BattleRepository() {
        this.map = new ConcurrentHashMap<>();
    }

    public boolean exists(UUID id) {
        return map.containsKey(id);
    }

    public Collection<Battle> findAll() {
        return map.values();
    }

    public Battle getById(UUID id) {
        return map.get(id);
    }

    public Battle save(Battle battle) {
        map.put(battle.getId(), battle);
        return battle;
    }

    public Battle update(UUID battleId, Consumer<Battle> consumer) {
        return Optional.ofNullable(map.computeIfPresent(battleId, (id, battle) -> {
            consumer.accept(battle);
            return battle;
        })).orElseThrow(() -> new IllegalArgumentException("The battle with battleId %s doesn't exist".formatted(battleId)));
    }

}
