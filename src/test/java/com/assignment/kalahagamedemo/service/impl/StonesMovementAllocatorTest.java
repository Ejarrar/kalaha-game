package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.PlayerSlot;
import com.assignment.kalahagamedemo.model.Slot;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.GameInitializer;
import com.assignment.kalahagamedemo.service.StonesMovementAllocator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.assignment.kalahagamedemo.model.Board.KALAHA_SLOT_INDEX;
import static com.assignment.kalahagamedemo.model.Board.TOTAL_SLOTS_COUNT;
import static com.assignment.kalahagamedemo.model.enums.Player.getOppositePlayer;

@SpringBootTest
@ActiveProfiles("test")
public class StonesMovementAllocatorTest {

    @Autowired
    private StonesMovementAllocator stonesMovementAllocator;

    @Autowired
    private GameInitializer gameInitializer;

    private Game game;

    private PlayerSlot playerSlot;

    @BeforeEach
    void setUp() {
        game = gameInitializer.init();
        playerSlot = PlayerSlot.builder()
                .playerSide(game.getPlayerTurn())
                .lastSlotReachedIndex(3)
                .build();
    }

    @Test
    void givenAnyPlayerSlotIndexWithStartedGameWhenFindPlayerLastSlotReachedThenShouldReturnCorrectPlayerSlotReached() {
        int currentSlotIndex = 1;
        Player playerTurn = game.getPlayerTurn();
        PlayerSlot playerLastSlotReached = stonesMovementAllocator.findPlayerLastSlotReached(currentSlotIndex, game);
        int stonesCount = game.getPlayerSlots(game.getPlayerTurn()).get(currentSlotIndex).getStonesCount();
        stonesCount += currentSlotIndex;
        playerSlot.setLastSlotReachedIndex(stonesCount % TOTAL_SLOTS_COUNT);
        playerSlot.setPlayerSide((stonesCount / TOTAL_SLOTS_COUNT) % 2 == 0 ? playerTurn : getOppositePlayer(playerTurn));

        Assertions.assertEquals(playerSlot, playerLastSlotReached);
    }

    @Test
    void givenAnyPlayerSlotIndexWithStartedGameWhenFindPlayerLastSlotReachedThenShouldReturnEmptySlotsInStartingIndex() {
        int currentSlotIndex = 1;
        Player playerTurn = game.getPlayerTurn();
        stonesMovementAllocator.findPlayerLastSlotReached(currentSlotIndex, game);

        Assertions.assertEquals(0, game.getPlayerSlots(playerTurn).get(currentSlotIndex).getStonesCount());
    }

    @Test
    void givenAnyPlayerSlotIndexWithStartedGameWhenCollectAllStonesFromOpponentMirrorSlotThenShouldEmptySlotsFromOpponent() {

        stonesMovementAllocator.collectAllStonesFromOpponentMirrorSlot(playerSlot, game);

        Assertions.assertEquals(0,
                game.getPlayerSlots(getOppositePlayer(playerSlot.getPlayerSide())).get(playerSlot.getLastSlotReachedIndex()).getStonesCount());
    }

    @Test
    void givenAnyPlayerSlotIndexWithStartedGameWhenCollectAllStonesFromOpponentMirrorSlotThenShouldAddOpponentStonesToActivePlayerSlot() {
        Slot slot = game.getPlayerSlots(playerSlot.getPlayerSide()).get(playerSlot.getLastSlotReachedIndex());
        Slot opponentSlot = game.getPlayerSlots(getOppositePlayer(playerSlot.getPlayerSide()))
                .get(Math.abs(playerSlot.getLastSlotReachedIndex() - TOTAL_SLOTS_COUNT));

        stonesMovementAllocator.collectAllStonesFromOpponentMirrorSlot(playerSlot, game);

        Assertions.assertEquals(slot.getStonesCount() + opponentSlot.getStonesCount(),
                game.getPlayerSlots(playerSlot.getPlayerSide()).get(playerSlot.getLastSlotReachedIndex()).getStonesCount());
    }

    @Test
    void givenAnyPlayerSlotIndexWithStartedGameWhenCollectAllStonesFromOpponentMirrorSlotThenShouldAddCollectedStonesToKalaha() {
        Slot playerSlot = game.getPlayerSlots(this.playerSlot.getPlayerSide()).get(this.playerSlot.getLastSlotReachedIndex());
        Slot opponentSlot = game.getPlayerSlots(getOppositePlayer(this.playerSlot.getPlayerSide()))
                .get(Math.abs(this.playerSlot.getLastSlotReachedIndex() - TOTAL_SLOTS_COUNT));

        stonesMovementAllocator.collectAllStonesFromOpponentMirrorSlot(this.playerSlot, game);

        Assertions.assertEquals(playerSlot.getStonesCount() + opponentSlot.getStonesCount(),
                game.getPlayerSlots(this.playerSlot.getPlayerSide()).get(KALAHA_SLOT_INDEX).getStonesCount());
    }

    @Test
    void givenAnyPlayerSlotIndexWithStartedGameWhenCollectRemainingStonesToKalahaThenShouldAddCollectedStonesToKalaha() {
        List<Slot> playerSlots = game.getPlayerSlots(playerSlot.getPlayerSide());
        List<Slot> opponentSlots = game.getPlayerSlots(getOppositePlayer(playerSlot.getPlayerSide()));
        int opponentStonesCount = opponentSlots.stream().filter(slot -> !slot.isKalaha()).mapToInt(Slot::getStonesCount).sum();
        int playerStonesCount = playerSlots.stream().filter(slot -> !slot.isKalaha()).mapToInt(Slot::getStonesCount).sum();

        stonesMovementAllocator.collectRemainingStonesToKalaha(game);

        Assertions.assertEquals(playerStonesCount, game.getPlayerSlots(playerSlot.getPlayerSide()).get(KALAHA_SLOT_INDEX).getStonesCount());
        Assertions.assertEquals(opponentStonesCount, game.getPlayerSlots(getOppositePlayer(playerSlot.getPlayerSide())).get(KALAHA_SLOT_INDEX).getStonesCount());
    }

    @Test
    void givenAnyPlayerSlotIndexWithStartedGameWhenCollectRemainingStonesToKalahaThenShouldEmptyAllPlayersSlots() {
        stonesMovementAllocator.collectRemainingStonesToKalaha(game);

        List<Slot> playerSlots = game.getPlayerSlots(playerSlot.getPlayerSide());
        List<Slot> opponentSlots = game.getPlayerSlots(getOppositePlayer(playerSlot.getPlayerSide()));
        int opponentStonesCount = opponentSlots.stream().filter(slot -> !slot.isKalaha()).mapToInt(Slot::getStonesCount).sum();
        int playerStonesCount = playerSlots.stream().filter(slot -> !slot.isKalaha()).mapToInt(Slot::getStonesCount).sum();

        Assertions.assertEquals(0, playerStonesCount);
        Assertions.assertEquals(0, opponentStonesCount);
    }
}
