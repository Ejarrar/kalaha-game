package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.request.MakeMoveRequest;
import com.assignment.kalahagamedemo.service.GameInitializer;
import com.assignment.kalahagamedemo.service.GameService;
import com.assignment.kalahagamedemo.service.MovementProcessor;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameInitializer gameInitializer;
    private final MovementProcessor movementProcessor;
    private Game gameInstance = null;

    public GameServiceImpl(GameInitializer gameInitializer, MovementProcessor movementProcessor) {
        this.gameInitializer = gameInitializer;
        this.movementProcessor = movementProcessor;
    }

    public Game getGame() {
        if (gameInstance == null)
            gameInstance = gameInitializer.init();

        return gameInstance;
    }

    public Game play(MakeMoveRequest moveRequest) {
        return movementProcessor.processMovement(moveRequest, this.gameInstance);
    }

}
