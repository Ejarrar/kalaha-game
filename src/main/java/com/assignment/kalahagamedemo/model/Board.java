package com.assignment.kalahagamedemo.model;

import com.assignment.kalahagamedemo.model.enums.Player;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Board {

    public static final int SLOTS_COUNT = 6;
    public static final int KALAHA_SLOT_INDEX = 6;
    public static final int TOTAL_SLOTS_COUNT = 7;
    private Map<Player, List<Slot>> playersSlotsMap;

}
