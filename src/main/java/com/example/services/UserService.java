package com.example.services;

import com.example.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.dtos.DeviceDTO;
import com.example.dtos.UserDTO;
import com.example.dtos.UserDetailsImpl;
import com.example.dtos.builders.DeviceBuilder;
import com.example.dtos.builders.UserBuilder;
import com.example.entities.Device;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new UserDetailsImpl(user);
    }

    public UserDTO findUserById (Long id) {
        return UserBuilder.toDto(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id {} not found", id)));

    }

    public List<UserDTO> findAllUsers(){
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
        return userList.stream()
                .map(UserBuilder::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByRole(Role role){
        List<User> userList = userRepository.findUsersByRole(role);
        return userList.stream()
                .map(UserBuilder::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> searchUsersByName (String name){
        return userRepository.findUserByNameContaining(name).stream()
                .map(UserBuilder::toDto)
                .collect(Collectors.toList());
    }

    public Long updateUser (Long userID, UserDTO userDTO){
        User foundUser = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User with id {} was not found in db",userID));
        LOGGER.error(foundUser.getId().toString());
        UserBuilder.update(foundUser, userDTO);
        LOGGER.debug("User with id {} was updated in db", foundUser.getId());
        userRepository.save(foundUser);

        return foundUser.getId();
    }

    public Long deleteUserById(Long userID){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User with id {} was not found in db",userID));
        userRepository.delete(user);
        LOGGER.debug("User with id {} was deleted", user.getId());
        return user.getId();
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
        LOGGER.debug("Oops..All users were deleted");
    }


    public List<DeviceDTO> getDevices(Long userID){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User with id {} was not found in db",userID));
        Set<Device> deviceList = user.getDevices();
        return deviceList.stream()
                .map(DeviceBuilder::toDto)
                .collect(Collectors.toList());
    }
}
