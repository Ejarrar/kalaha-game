package com.assignment.kalahagamedemo.service;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.PlayerSlot;

public interface StonesMovementAllocator {
    PlayerSlot findPlayerLastSlotReached(int currentSlotIndex, Game game);

    void collectRemainingStonesToKalaha(Game game);

    void collectAllStonesFromOpponentMirrorSlot(PlayerSlot playerSlot, Game game);

}
