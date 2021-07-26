package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.PlayerSlot;
import com.assignment.kalahagamedemo.model.request.MakeMoveRequest;
import com.assignment.kalahagamedemo.service.GameRulesChecker;
import com.assignment.kalahagamedemo.service.GameStatusHandler;
import com.assignment.kalahagamedemo.service.MovementProcessor;
import com.assignment.kalahagamedemo.service.StonesMovementAllocator;
import org.springframework.stereotype.Service;

@Service
public class MovementProcessorImpl implements MovementProcessor {

    private final StonesMovementAllocator stonesMovementAllocator;
    private final GameRulesChecker gameRulesChecker;
    private final GameStatusHandler gameStatusHandler;

    public MovementProcessorImpl(StonesMovementAllocator stonesMovementAllocator, GameRulesChecker gameRulesChecker, GameStatusHandler gameStatusHandler) {
        this.stonesMovementAllocator = stonesMovementAllocator;
        this.gameRulesChecker = gameRulesChecker;
        this.gameStatusHandler = gameStatusHandler;
    }

    @Override
    public Game processMovement(MakeMoveRequest moveRequest, Game game) {
        PlayerSlot playerSlot = stonesMovementAllocator.findPlayerLastSlotReached(moveRequest.getIndex(), game);

        if (gameRulesChecker.checkIfPlayerSlotReachedHasOneStone(playerSlot, game)) {
            stonesMovementAllocator.collectAllStonesFromOpponentMirrorSlot(playerSlot, game);
        }
        if (gameRulesChecker.checkIfGameIsOver(game)) {
            stonesMovementAllocator.collectRemainingStonesToKalaha(game);
            gameStatusHandler.changeGameStatus(game);
            gameStatusHandler.setWinner(game);
        }
        if (!gameRulesChecker.checkIfReachedSlotIsKalaha(playerSlot, game)) {
            gameStatusHandler.switchPlayerTurns(game);
        }
        return game;
    }
}
