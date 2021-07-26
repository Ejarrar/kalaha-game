package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.configuration.properties.KalahaSlotProperties;
import com.assignment.kalahagamedemo.model.Board;
import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.Slot;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.GameInitializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.assignment.kalahagamedemo.model.enums.GameStatus.STARTED;
import static com.assignment.kalahagamedemo.model.enums.Player.*;

@Service
public class GameInitializerImpl implements GameInitializer {

    private final KalahaSlotProperties kalahaSlotProperties;

    public GameInitializerImpl(KalahaSlotProperties kalahaSlotProperties) {
        this.kalahaSlotProperties = kalahaSlotProperties;
    }

    public Game init() {
        return Game.builder()
                .board(Board.builder()
                        .playersSlotsMap(getPlayerSlots())
                        .build())
                .gameStatus(STARTED)
                .playerTurn(getRandomPlayer())
                .build();
    }

    private Map<Player, List<Slot>> getPlayerSlots() {
        return Map.of(PLAYER_ONE, Stream.concat(Stream.generate(() -> new Slot(kalahaSlotProperties.getStonesCount(), false)).limit(Board.SLOTS_COUNT),
                Stream.of(new Slot(0, true))).collect(Collectors.toList())
                , PLAYER_TWO, Stream.concat(Stream.generate(() -> new Slot(kalahaSlotProperties.getStonesCount(), false)).limit(Board.SLOTS_COUNT),
                        Stream.of(new Slot(0, true))).collect(Collectors.toList()));
    }

}
