package com.assignment.kalahagamedemo.service.impl;


import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.enums.GameStatus;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.GameInitializer;
import com.assignment.kalahagamedemo.service.GameStatusHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class GameStatusHandlerTest {


    private Game game;

    @Autowired
    private GameStatusHandler gameStatusHandler;
    @Autowired
    private GameInitializer gameInitializer;


    @BeforeEach
    void setUp() {
        game = gameInitializer.init();
    }

    @Test
    void givenGameWithPlayerTurnIsRandomWhenSwitchPlayerTurnsThenShouldReturnOppositePlayer() {
        Player activePlayer = game.getPlayerTurn();

        gameStatusHandler.switchPlayerTurns(game);

        Assertions.assertNotEquals(activePlayer, game.getPlayerTurn());
    }

    @Test
    void givenGameIsOverWhenChangeGameStatusThenShouldReturnGameOverStatus() {
        GameStatus gameStatus = game.getGameStatus();

        gameStatusHandler.changeGameStatus(game);

        Assertions.assertNotEquals(gameStatus, game.getGameStatus());
    }
}
