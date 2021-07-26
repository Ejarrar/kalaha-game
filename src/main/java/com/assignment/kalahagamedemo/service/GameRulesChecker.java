package com.assignment.kalahagamedemo.service;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.PlayerSlot;

public interface GameRulesChecker {

    boolean checkIfPlayerSlotReachedHasOneStone(PlayerSlot playerSlot, Game game);

    boolean checkIfGameIsOver(Game game);

    boolean checkIfReachedSlotIsKalaha(PlayerSlot playerSlot, Game game);

}
