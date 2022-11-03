package com.example.controllers;

import com.example.UrlMapping;
import com.example.dtos.DeviceDTO;
import com.example.services.MonitoredValueService;
import com.example.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(UrlMapping.CLIENT)
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private MonitoredValueService monitoredValueService;

    @GetMapping(UrlMapping.DAILY_CONSUMPTION)
    public ResponseEntity<Map<Integer, Float>> getDailyEnergyConsumption(@PathVariable(value = "id") Long deviceID, @PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        Map<Integer, Float> map = monitoredValueService.getDailyEnergyConsumption(deviceID, date);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(UrlMapping.USER_DEVICES)
    public ResponseEntity<List<DeviceDTO>> getUserDevices(@PathVariable(value = "id") Long userID){
        List<DeviceDTO> deviceDetailsDTOList = userService.getDevices(userID);
        return new ResponseEntity<>(deviceDetailsDTOList, HttpStatus.OK);
    }


}
