package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.model.Board;
import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.enums.GameStatus;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.GameStatusHandler;
import org.springframework.stereotype.Service;

@Service
public class GameStatusHandlerImpl implements GameStatusHandler {


    @Override
    public void switchPlayerTurns(Game game) {
        game.setPlayerTurn(Player.getOppositePlayer(game.getPlayerTurn()));
    }

    @Override
    public void changeGameStatus(Game game) {
        game.setGameStatus(GameStatus.OVER);
    }

    @Override
    public void setWinner(Game game) {
        int playerOneKalahaStonesCount = game.getPlayerSlots(Player.PLAYER_ONE).get(Board.KALAHA_SLOT_INDEX).getStonesCount();
        int playerTwoKalahaStonesCount = game.getPlayerSlots(Player.PLAYER_TWO).get(Board.KALAHA_SLOT_INDEX).getStonesCount();

        game.setWinner(playerOneKalahaStonesCount > playerTwoKalahaStonesCount ? Player.PLAYER_ONE : Player.PLAYER_TWO);
    }
}
