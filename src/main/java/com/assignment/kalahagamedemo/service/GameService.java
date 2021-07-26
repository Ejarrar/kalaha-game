package com.assignment.kalahagamedemo.service;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.request.MakeMoveRequest;

public interface GameService {

    Game getGame();

    Game play(MakeMoveRequest moveRequest);

}
