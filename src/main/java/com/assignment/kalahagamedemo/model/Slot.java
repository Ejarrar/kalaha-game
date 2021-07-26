package com.assignment.kalahagamedemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Slot {

    private int stonesCount;
    private boolean isKalaha;

}
