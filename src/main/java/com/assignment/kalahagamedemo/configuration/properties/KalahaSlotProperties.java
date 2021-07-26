package com.assignment.kalahagamedemo.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config.kalaha-game")
@Data
public class KalahaSlotProperties {

    private int stonesCount;

}