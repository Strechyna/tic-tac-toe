package com.example.tictactoe.battleservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public record Player(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
    public Player {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(name, "Name must not be null");
    }
}
