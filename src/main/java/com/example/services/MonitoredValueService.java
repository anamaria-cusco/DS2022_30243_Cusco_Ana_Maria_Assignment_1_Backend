package com.example.services;

import com.example.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.dtos.MonitoredValueDTO;
import com.example.dtos.builders.MonitoredValueBuilder;
import com.example.entities.Device;
import com.example.entities.MonitoredValue;
import com.example.repositories.MonitoredValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repositories.DeviceRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MonitoredValueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoredValueService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private MonitoredValueRepository monitoredValueRepository;
    @Autowired
    private DeviceRepository deviceRepository;


    public List<MonitoredValueDTO> findAll (){
        return monitoredValueRepository.findAll().stream()
                .map(MonitoredValueBuilder::toDto)
                .collect(Collectors.toList());
    }

    public List<MonitoredValueDTO> getMonitoredValues() {
        return monitoredValueRepository.findAll().stream().map(MonitoredValueBuilder::toDto)
                .collect(Collectors.toList());
    }

    public Long addMonitoredValue(Long deviceID, MonitoredValueDTO monitoredValueDTO){
        Device device = deviceRepository.findById(deviceID).orElseThrow(() -> new ResourceNotFoundException("Device with id {} was not found in db",deviceID));
        MonitoredValue monitoredValue = MonitoredValueBuilder.toEntity(monitoredValueDTO);
        monitoredValue.setDevice(device);
        monitoredValue = monitoredValueRepository.save(monitoredValue);
        LOGGER.debug("monitoredValue with id {} was inserted in db", monitoredValue.getId());
        return monitoredValue.getId();
    }

    public Set<MonitoredValueDTO> getMonitoredValuesOfDevice(Long deviceID) {
        Device device = deviceRepository.findById(deviceID).orElseThrow(() -> new ResourceNotFoundException("Device with id {} was not found in db",deviceID));
        return device.getMonitoredValues().stream()
                .map(MonitoredValueBuilder::toDto)
                .collect(Collectors.toSet());
    }

    public Map<Integer, Float> getDailyEnergyConsumption(Long deviceID, LocalDate date){
        Map<Integer, Float> map = new TreeMap<>();
        Device device = deviceRepository.findById(deviceID).orElseThrow(() -> new ResourceNotFoundException("Device with id {} was not found in db",deviceID));
        Set<MonitoredValue> monitoredValues = device.getMonitoredValues();
        for (MonitoredValue monitoredValue : monitoredValues) {
            LocalDate monitoredValueDate = monitoredValue.getTimestamp().toLocalDate();
            int hour = monitoredValue.getTimestamp().getHour();
            if (date.equals(monitoredValueDate)) {
                map.put(hour, monitoredValue.getEnergyConsumption());

            }
        }
        return map;
    }
}
