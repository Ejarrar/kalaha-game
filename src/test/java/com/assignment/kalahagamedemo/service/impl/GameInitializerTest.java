package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.configuration.properties.KalahaSlotProperties;
import com.assignment.kalahagamedemo.model.Board;
import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.Slot;
import com.assignment.kalahagamedemo.model.enums.GameStatus;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.GameInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameInitializerTest {

    @Autowired
    private GameInitializer gameInitializer;

    @Autowired
    private KalahaSlotProperties kalahaSlotProperties;


    @Test
    void givenDefaultGameConfigWhenGetGameThenReturnBoardWithFilledSlots() {

        Game game = gameInitializer.init();
        Map<Player, List<Slot>> slotsMap = Map.of(Player.PLAYER_ONE, Stream.concat(Stream.generate(() -> new Slot(kalahaSlotProperties.getStonesCount(), false)).limit(Board.SLOTS_COUNT),
                Stream.of(new Slot(0, true))).collect(Collectors.toList())
                , Player.PLAYER_TWO, Stream.concat(Stream.generate(() -> new Slot(kalahaSlotProperties.getStonesCount(), false)).limit(Board.SLOTS_COUNT),
                        Stream.of(new Slot(0, true))).collect(Collectors.toList()));

        Assertions.assertEquals(slotsMap, game.getBoard().getPlayersSlotsMap());
    }

    @Test
    void givenDefaultGameConfigWhenGetGameThenReturnGameStatusStarted() {

        Game game = gameInitializer.init();

        Assertions.assertEquals(GameStatus.STARTED, game.getGameStatus());
    }
}
