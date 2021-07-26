package com.assignment.kalahagamedemo.model;

import com.assignment.kalahagamedemo.model.enums.GameStatus;
import com.assignment.kalahagamedemo.model.enums.Player;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Game {

    private Board board;
    private GameStatus gameStatus;
    private Player playerTurn;
    private Player winner;

    public List<Slot> getPlayerSlots(Player player) {
        return this.board.getPlayersSlotsMap().get(player);
    }

}
