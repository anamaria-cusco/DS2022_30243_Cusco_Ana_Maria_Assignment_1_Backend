package com.example.controllers;

import com.example.UrlMapping;
import com.example.dtos.MonitoredValueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.services.MonitoredValueService;

import java.util.Set;


@RestController
@RequestMapping(UrlMapping.ADMIN_MONITORED_VALUES)
@RequiredArgsConstructor
public class MonitoredValueController {
    @Autowired
    private MonitoredValueService monitoredValueService;

    @GetMapping(UrlMapping.DEVICE_MONITORED_VALUES)
    public ResponseEntity<Set<MonitoredValueDTO>> getMonitoredValuesOfDevice(@PathVariable(value = "id") Long deviceID){
        Set<MonitoredValueDTO> monitoredValueDTOSet = monitoredValueService.getMonitoredValuesOfDevice(deviceID);
        return new ResponseEntity<>(monitoredValueDTOSet, HttpStatus.OK);
    }

    @PostMapping(UrlMapping.ADD_MONITORED_VALUE)
    public ResponseEntity<Long> getMonitoredValuesOfUser(@PathVariable(value = "id") Long monitoredValueID, @RequestBody MonitoredValueDTO monitoredValueDTO){
        Long monitoredValueSavedID = monitoredValueService.addMonitoredValue(monitoredValueID, monitoredValueDTO);
        return new ResponseEntity<>(monitoredValueSavedID, HttpStatus.OK);
    }


}
