package com.example.dtos.builders;

import com.example.dtos.UserDTO;
import com.example.entities.User;

public class UserBuilder {
    private UserBuilder(){}

    public static User toEntity(UserDTO userDto){
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .username(userDto.getUsername())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }

    public static UserDTO toDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .address(user.getAddress())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    public static User update(User user, UserDTO userDTO){
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        return user;
    }
}
