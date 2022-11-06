package com.example.controllers;

import com.example.UrlMapping;
import com.example.dtos.DeviceDTO;
import com.example.dtos.UserDTO;
import com.example.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;


@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin
@RequestMapping(UrlMapping.ADMIN_DEVICES)
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping(UrlMapping.ID)
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("id") Long deviceID) {
        DeviceDTO deviceDTO = deviceService.findDeviceById(deviceID);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }
    @GetMapping(UrlMapping.ALL_DEVICES)
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> deviceDTOS = deviceService.findAllDevices();
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @PutMapping(UrlMapping.EDIT_DEVICE)
    public ResponseEntity<Long> editDevice(@PathVariable(value = "id") Long id, @RequestBody DeviceDTO deviceDTO) {
        Long sensorId = deviceService.updateDevice(id, deviceDTO);
        return new ResponseEntity<>(sensorId, HttpStatus.OK);
    }

    @DeleteMapping(UrlMapping.DELETE_DEVICE)
    public ResponseEntity<Long> deleteDevice(@PathVariable(value = "id") Long id) {
        Long deletedDeviceID = deviceService.deleteDeviceById(id);
        return new ResponseEntity<>(deletedDeviceID, HttpStatus.OK);
    }
    @DeleteMapping(UrlMapping.DELETE_ALL_DEVICES)
    public ResponseEntity<String> deleteAllDevices() {
        deviceService.deleteAllDevices();
        return new ResponseEntity<>("All devices were deleted",HttpStatus.OK);
    }

    @GetMapping(UrlMapping.SEARCH_DEVICES_BY_DESCRIPTION)
    public ResponseEntity<List<DeviceDTO>> searchDevicesByDescription(@PathVariable(value = "description") String description){
        List<DeviceDTO> deviceDTOList = deviceService.searchDevicesByDescription(description);
        return new ResponseEntity<>(deviceDTOList, HttpStatus.OK);
    }


    @PostMapping(UrlMapping.ADD_DEVICE)
    public ResponseEntity<Long> assignDeviceToUser(@PathVariable(value = "id") Long userID, @Valid @RequestBody DeviceDTO deviceDTO) {
        Long deviceID = deviceService.addDevice(userID, deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }


}
