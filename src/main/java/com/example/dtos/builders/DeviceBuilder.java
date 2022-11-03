package com.example.dtos.builders;

import com.example.dtos.DeviceDTO;
import com.example.entities.Device;

public class DeviceBuilder {
    private DeviceBuilder(){

    }

    public static Device toEntity (DeviceDTO deviceDTO){
        return Device.builder()
                .id(deviceDTO.getId())
                .address(deviceDTO.getAddress())
                .description(deviceDTO.getDescription())
                .maxConsumption(deviceDTO.getMaxConsumption())
                .build();
    }

    public static DeviceDTO toDto (Device device){
        return DeviceDTO.builder()
                .id(device.getId())
                .address(device.getAddress())
                .description(device.getDescription())
                .maxConsumption(device.getMaxConsumption())
                .build();
    }

    public static Device update(Device device, DeviceDTO deviceDTO){
        device.setDescription(deviceDTO.getDescription());
        device.setAddress(deviceDTO.getAddress());
        device.setMaxConsumption(deviceDTO.getMaxConsumption());
        return device;
    }
}
