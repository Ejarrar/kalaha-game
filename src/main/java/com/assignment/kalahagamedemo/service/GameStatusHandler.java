package com.assignment.kalahagamedemo.service;

import com.assignment.kalahagamedemo.model.Game;

public interface GameStatusHandler {

    void switchPlayerTurns(Game game);

    void changeGameStatus(Game game);

    void setWinner(Game game);
}
