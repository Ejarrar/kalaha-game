package com.assignment.kalahagamedemo.service.impl;

import com.assignment.kalahagamedemo.model.Board;
import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.PlayerSlot;
import com.assignment.kalahagamedemo.model.Slot;
import com.assignment.kalahagamedemo.model.enums.Player;
import com.assignment.kalahagamedemo.service.GameInitializer;
import com.assignment.kalahagamedemo.service.GameRulesChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class GameRulesCheckerTest {


    @Autowired
    private GameRulesChecker GameRulesChecker;
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
    void givenPlayerSlotWithOneStoneWhenCheckIfPlayerSlotReachedHasOneStoneThenReturnTrue() {

        List<Slot> slots = game.getBoard().getPlayersSlotsMap().get(playerSlot.getPlayerSide());
        Slot slot = slots.get(3);
        slot.setStonesCount(1);
        slots.set(3, slot);

        Assertions.assertTrue(GameRulesChecker.checkIfPlayerSlotReachedHasOneStone(playerSlot, game));
    }

    @Test
    void givenPlayerSlotWithOneStoneButPlayerSideIsOpponentSideWhenCheckIfPlayerSlotReachedHasOneStoneThenReturnFalse() {

        playerSlot.setPlayerSide(Player.getOppositePlayer(playerSlot.getPlayerSide()));
        List<Slot> slots = game.getBoard().getPlayersSlotsMap().get(playerSlot.getPlayerSide());
        Slot slot = slots.get(3);
        slot.setStonesCount(1);
        slots.set(3, slot);

        Assertions.assertFalse(GameRulesChecker.checkIfPlayerSlotReachedHasOneStone(playerSlot, game));
    }

    @Test
    void givenPlayerSlotWithOneStoneButSlotIsKalahaWhenCheckIfPlayerSlotReachedHasOneStoneThenReturnFalse() {

        List<Slot> slots = game.getBoard().getPlayersSlotsMap().get(playerSlot.getPlayerSide());
        Slot slot = slots.get(3);
        slot.setStonesCount(1);
        slot.setKalaha(true);
        slots.set(3, slot);

        Assertions.assertFalse(GameRulesChecker.checkIfPlayerSlotReachedHasOneStone(playerSlot, game));
    }

    @Test
    void givenPlayerSlotWithMoreThanOneStoneWhenCheckIfPlayerSlotReachedHasOneStoneThenReturnFalse() {

        List<Slot> slots = game.getBoard().getPlayersSlotsMap().get(playerSlot.getPlayerSide());
        Slot slot = slots.get(3);
        slot.setStonesCount(2);
        slots.set(3, slot);

        Assertions.assertFalse(GameRulesChecker.checkIfPlayerSlotReachedHasOneStone(playerSlot, game));
    }

    @Test
    void givenPlayerSlotIsPlayerTurnAndIndexIsKalahaWhenCheckIfReachedSlotIsKalahaThenReturnTrue() {
        playerSlot.setLastSlotReachedIndex(Board.KALAHA_SLOT_INDEX);

        Assertions.assertTrue(GameRulesChecker.checkIfReachedSlotIsKalaha(playerSlot, game));
    }

    @Test
    void givenPlayerSlotIsPlayerTurnAndIndexIsNotKalahaWhenCheckIfReachedSlotIsKalahaThenReturnFalse() {
        playerSlot.setLastSlotReachedIndex(1);

        Assertions.assertFalse(GameRulesChecker.checkIfReachedSlotIsKalaha(playerSlot, game));
    }

    @Test
    void givenPlayerSlotIsOpponentPlayerTurnAndIndexIsKalahaWhenCheckIfReachedSlotIsKalahaThenReturnFalse() {
        playerSlot.setLastSlotReachedIndex(Board.KALAHA_SLOT_INDEX);
        playerSlot.setPlayerSide(Player.getOppositePlayer(playerSlot.getPlayerSide()));

        Assertions.assertFalse(GameRulesChecker.checkIfReachedSlotIsKalaha(playerSlot, game));
    }

    @Test
    void givenGameBoardOfPlayerOneWithEmptySlotsWhenCheckIfGameIsOverThenReturnTrue() {
        List<Slot> slots = game.getBoard().getPlayersSlotsMap().get(Player.PLAYER_ONE);
        slots.stream().filter(slot -> !slot.isKalaha()).forEach(slot -> slot.setStonesCount(0));

        Assertions.assertTrue(GameRulesChecker.checkIfGameIsOver(game));
    }

    @Test
    void givenGameBoardOfPlayerTwoWithEmptySlotsWhenCheckIfGameIsOverThenReturnTrue() {
        List<Slot> slots = game.getBoard().getPlayersSlotsMap().get(Player.PLAYER_TWO);
        slots.stream().filter(slot -> !slot.isKalaha()).forEach(slot -> slot.setStonesCount(0));

        Assertions.assertTrue(GameRulesChecker.checkIfGameIsOver(game));
    }

    @Test
    void givenGameBoardOfPlayerOneWithFilledSlotsWhenCheckIfGameIsOverThenReturnFalse() {
        List<Slot> slots = game.getBoard().getPlayersSlotsMap().get(Player.PLAYER_ONE);
        slots.stream().filter(slot -> !slot.isKalaha()).forEach(slot -> slot.setStonesCount(1));

        Assertions.assertFalse(GameRulesChecker.checkIfGameIsOver(game));
    }

}
