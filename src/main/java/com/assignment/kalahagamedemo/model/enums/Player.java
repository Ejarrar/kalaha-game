package com.assignment.kalahagamedemo.model.enums;

import java.util.List;
import java.util.Random;

public enum Player {

    PLAYER_ONE(1), PLAYER_TWO(2);

    private static final List<Player> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private final int playerCode;

    Player(int playerCode) {
        this.playerCode = playerCode;
    }

    public static Player getOppositePlayer(Player player) {
        return player.playerCode == 1 ? PLAYER_TWO : PLAYER_ONE;
    }

    public static Player getRandomPlayer() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
