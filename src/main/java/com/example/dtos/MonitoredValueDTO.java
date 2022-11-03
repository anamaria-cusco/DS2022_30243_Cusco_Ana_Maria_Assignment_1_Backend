package com.example.dtos;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
@Setter
public class MonitoredValueDTO {
    private Long id;
    private String timestamp;
    private float energyConsumption;
}
