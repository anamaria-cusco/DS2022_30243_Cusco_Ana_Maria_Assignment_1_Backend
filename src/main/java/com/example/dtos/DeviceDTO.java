package com.example.dtos;


import com.example.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DeviceDTO {
    private Long id;
    private String description;
    private String address;
    private float maxConsumption;



}
