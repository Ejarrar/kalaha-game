package com.assignment.kalahagamedemo.model;

import com.assignment.kalahagamedemo.model.enums.Player;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerSlot {

    private int lastSlotReachedIndex;
    private Player playerSide;
}
