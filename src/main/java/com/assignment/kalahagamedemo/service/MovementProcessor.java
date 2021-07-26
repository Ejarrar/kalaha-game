package com.assignment.kalahagamedemo.service;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.request.MakeMoveRequest;

public interface MovementProcessor {

    Game processMovement(MakeMoveRequest movement, Game game);
}
