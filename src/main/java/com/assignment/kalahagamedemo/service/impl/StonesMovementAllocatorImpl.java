package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.model.Board;
import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.PlayerSlot;
import com.assignment.kalahagamedemo.model.Slot;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.StonesMovementAllocator;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.assignment.kalahagamedemo.model.Board.KALAHA_SLOT_INDEX;
import static com.assignment.kalahagamedemo.model.enums.Player.getOppositePlayer;

@Service
public class StonesMovementAllocatorImpl implements StonesMovementAllocator {

    @Override
    public PlayerSlot findPlayerLastSlotReached(int currentSlotIndex, Game game) {
        int stones = getStones(currentSlotIndex, game);

        PlayerSlot playerSlot = PlayerSlot.builder()
                .playerSide(game.getPlayerTurn())
                .lastSlotReachedIndex(currentSlotIndex)
                .build();

        while (stones > 0) {
            playerSlot.setLastSlotReachedIndex(getNextSlot(playerSlot));
            addOneStoneIntoNextSlot(playerSlot, game);
            stones--;
        }
        emptyStonesInSlot(currentSlotIndex, game);
        return playerSlot;
    }

    @Override
    public void collectRemainingStonesToKalaha(Game game) {
        List<Slot> playerOneSlots = game.getPlayerSlots(Player.PLAYER_ONE);
        List<Slot> playerTwoSlots = game.getPlayerSlots(Player.PLAYER_TWO);

        Slot playerOneKalaha = playerOneSlots.get(KALAHA_SLOT_INDEX);
        playerOneSlots.stream().filter(slot -> !slot.isKalaha()).forEach(slot -> collectStonesToKalaha(playerOneKalaha, slot));
        Slot playerTwoKalaha = playerTwoSlots.get(KALAHA_SLOT_INDEX);
        playerTwoSlots.stream().filter(slot -> !slot.isKalaha()).forEach(slot -> collectStonesToKalaha(playerTwoKalaha, slot));
    }

    @Override
    public void collectAllStonesFromOpponentMirrorSlot(PlayerSlot playerSlot, Game game) {
        Slot currentSlot = game.getPlayerSlots(playerSlot.getPlayerSide()).get(playerSlot.getLastSlotReachedIndex());

        Slot playerKalahaSlot = game.getPlayerSlots(playerSlot.getPlayerSide()).get(Board.KALAHA_SLOT_INDEX);

        Slot opponentSlot = game.getPlayerSlots(Player.getOppositePlayer(playerSlot.getPlayerSide()))
                .get(Math.abs(playerSlot.getLastSlotReachedIndex() - Board.TOTAL_SLOTS_COUNT));

        int collectedStones = collectStonesFromSlot(currentSlot) + collectStonesFromSlot(opponentSlot);

        allocateStonesToKalaha(playerKalahaSlot, collectedStones);
    }

    private int getNextSlot(PlayerSlot playerSlot) {
        return (playerSlot.getLastSlotReachedIndex() + 1) % Board.TOTAL_SLOTS_COUNT;
    }

    private void addOneStoneIntoNextSlot(PlayerSlot playerSlot, Game game) {
        Board board = game.getBoard();
        Player player = game.getPlayerTurn();
        if (playerSlot.getLastSlotReachedIndex() <= Board.TOTAL_SLOTS_COUNT) {
            playerSlot.setPlayerSide(player);
            addOneStoneIntoNextSlotBasedOnPlayer(playerSlot.getLastSlotReachedIndex(), board, player);
        } else {
            playerSlot.setPlayerSide(getOppositePlayer(player));
            addOneStoneIntoNextSlotBasedOnPlayer(Math.abs(playerSlot.getLastSlotReachedIndex() - Board.TOTAL_SLOTS_COUNT), board, playerSlot.getPlayerSide());
        }
    }

    private void addOneStoneIntoNextSlotBasedOnPlayer(int lastSlotReached, Board board, Player player) {
        List<Slot> slots = board.getPlayersSlotsMap().get(player);
        updateStonesCountInSlot(lastSlotReached, slots, slots.get(lastSlotReached).getStonesCount() + 1);
    }

    private void emptyStonesInSlot(int currentSlotIndex, Game game) {
        Player activePlayer = game.getPlayerTurn();
        List<Slot> slots = game.getPlayerSlots(activePlayer);
        updateStonesCountInSlot(currentSlotIndex, slots, 0);
    }

    private void updateStonesCountInSlot(int index, List<Slot> slots, int newStonesCount) {
        Slot slot = slots.get(index);
        slot.setStonesCount(newStonesCount);
        slots.set(index, slot);
    }

    private int getStones(int index, Game game) {
        return game.getPlayerSlots(game.getPlayerTurn()).get(index).getStonesCount();
    }

    private void collectStonesToKalaha(Slot playerOneKalaha, Slot slot) {
        playerOneKalaha.setStonesCount(playerOneKalaha.getStonesCount() + slot.getStonesCount());
        slot.setStonesCount(0);
    }

    public void allocateStonesToKalaha(Slot kalahaSlot, int collectedStones) {
        kalahaSlot.setStonesCount(kalahaSlot.getStonesCount() + collectedStones);
    }

    private int collectStonesFromSlot(Slot slot) {
        int stonesCount = slot.getStonesCount();
        slot.setStonesCount(0);
        return stonesCount;
    }
}
