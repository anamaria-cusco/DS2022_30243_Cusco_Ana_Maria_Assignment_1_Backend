package com.example.dtos.builders;

import com.example.dtos.MonitoredValueDTO;
import com.example.entities.MonitoredValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MonitoredValueBuilder {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static MonitoredValue toEntity(MonitoredValueDTO monitoredValueDTO){
        return MonitoredValue.builder()
                .timestamp(LocalDateTime.parse(monitoredValueDTO.getTimestamp(), formatter))
                .energyConsumption(monitoredValueDTO.getEnergyConsumption())
                .build();
    }

    public static MonitoredValueDTO toDto(MonitoredValue monitoredValue){
        return MonitoredValueDTO.builder()
                .id(monitoredValue.getId())
                .timestamp(monitoredValue.getTimestamp().format(formatter))
                .energyConsumption(monitoredValue.getEnergyConsumption())
                .build();
    }
}
