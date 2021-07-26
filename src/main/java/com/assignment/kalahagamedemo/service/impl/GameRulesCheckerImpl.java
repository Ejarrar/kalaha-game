package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.PlayerSlot;
import com.assignment.kalahagamedemo.model.Slot;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.GameRulesChecker;
import org.springframework.stereotype.Service;

@Service
public class GameRulesCheckerImpl implements GameRulesChecker {

    @Override
    public boolean checkIfPlayerSlotReachedHasOneStone(PlayerSlot playerSlot, Game game) {
        Slot currentSlot = game.getPlayerSlots(playerSlot.getPlayerSide()).get(playerSlot.getLastSlotReachedIndex());

        return isCurrentSlotContainOneStone(currentSlot) && isNormalSlot(currentSlot) && isActivePlayerSlot(playerSlot.getPlayerSide(), game.getPlayerTurn());
    }

    @Override
    public boolean checkIfGameIsOver(Game game) {
        return game.getPlayerSlots(Player.PLAYER_ONE).stream().allMatch(slot -> slot.getStonesCount() == 0)
                || game.getPlayerSlots(Player.PLAYER_TWO).stream().allMatch(slot -> slot.getStonesCount() == 0);
    }

    @Override
    public boolean checkIfReachedSlotIsKalaha(PlayerSlot playerSlot, Game game) {
        return playerSlot.getPlayerSide().equals(game.getPlayerTurn())
                && game.getPlayerSlots(game.getPlayerTurn()).get(playerSlot.getLastSlotReachedIndex()).isKalaha();
    }


    private boolean isCurrentSlotContainOneStone(Slot slot) {
        return slot.getStonesCount() == 1;
    }

    private boolean isNormalSlot(Slot slot) {
        return !slot.isKalaha();
    }

    private boolean isActivePlayerSlot(Player playerSide, Player activePlayer) {
        return playerSide.equals(activePlayer);
    }
}
