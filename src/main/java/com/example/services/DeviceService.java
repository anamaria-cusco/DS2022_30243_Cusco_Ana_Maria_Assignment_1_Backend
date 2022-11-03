package com.example.services;

import com.example.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.dtos.DeviceDTO;
import com.example.dtos.builders.DeviceBuilder;
import com.example.entities.Device;
import com.example.entities.User;
import com.example.repositories.DeviceRepository;
import com.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    public List<DeviceDTO> findAllDevices(){
        return deviceRepository.findAll().stream()
                .map(DeviceBuilder::toDto)
                .collect(Collectors.toList());
    }

    public DeviceDTO findDeviceById (Long id){
        return DeviceBuilder.toDto(deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device with id {} not found", id)));
    }

    public Long addDevice(Long userID, DeviceDTO deviceDTO) {
        System.out.println("Before:"+deviceDTO.getMaxConsumption());
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User with id {} was not found in db",userID));
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device.setUser(user);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was added in db", device.getId());
        return device.getId();
    }

    public Long updateDevice(Long deviceID, DeviceDTO deviceDTO){

        Device foundDevice = deviceRepository.findById(deviceID)
                .orElseThrow(() -> new ResourceNotFoundException("Device with id {} was not found in db",deviceID));
        foundDevice = DeviceBuilder.update(foundDevice, deviceDTO);
        LOGGER.debug("Device with id {} was updated in db", foundDevice.getId());
        deviceRepository.save(foundDevice);
        return foundDevice.getId();
    }

    public Long deleteDeviceById (Long deviceID){
        Device device = deviceRepository.findById(deviceID)
                .orElseThrow(() -> new ResourceNotFoundException("Device with id {} was not found in db",deviceID));
        deviceRepository.deleteById(deviceID);
        LOGGER.debug("Device with id {} was deleted in db", deviceID);
        return device.getId();
    }

}
