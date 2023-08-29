package com.example.tictactoe.battleservice.controller;

import com.example.tictactoe.battleservice.dto.request.Join;
import com.example.tictactoe.battleservice.dto.request.MakeMove;
import com.example.tictactoe.battleservice.dto.battle.Battle;
import com.example.tictactoe.battleservice.dto.battle.Move;
import com.example.tictactoe.battleservice.service.BattleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Controller
@RequiredArgsConstructor
public class BattleWebSocketController {

    private final BattleService battleService;

    @MessageMapping("battle/{battleId}/join")
    @SendTo("topic/battle/{battleId}")
    public Battle join(@DestinationVariable @NotNull UUID battleId,
                       @Payload @NotNull @org.hibernate.validator.constraints.UUID String playerId) {
        Battle battle = battleService.get(battleId);
        return battleService.join(new Join(battle, UUID.fromString(playerId)));
    }

    @MessageMapping("battle/{battleId}/interrupt")
    @SendTo("topic/battle/{battleId}")
    public Battle interrupt(@DestinationVariable @NotNull UUID battleId) {
        return battleService.interrupt(battleId);
    }

    @MessageMapping("battle/{battleId}/move")
    @SendTo("topic/battle/{battleId}")
    public Battle makeMove(@DestinationVariable @NotNull UUID battleId,
                           @Payload @NotNull @Valid Move move) {
        Battle battle = battleService.get(battleId);
        return battleService.makeMove(new MakeMove(battle, move));
    }

}
