package com.example.controllers;

import com.example.UrlMapping;
import com.example.dtos.UserDTO;
import com.example.services.AuthService;
import com.example.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(UrlMapping.ADMIN_USERS)
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AuthService authService;
    final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(UrlMapping.ID)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long userID) {
        UserDTO userDTO = userService.findUserById(userID);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(UrlMapping.ALL_USERS)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userService.findAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PutMapping(UrlMapping.EDIT_USER)
    public ResponseEntity<Long> edit (@PathVariable(value = "id") Long userID, @RequestBody UserDTO userDTO) {
        Long updatedUserID = userService.updateUser(userID,userDTO);
        return new ResponseEntity<>(updatedUserID, HttpStatus.OK);
    }


    @DeleteMapping(UrlMapping.DELETE_USER)
    public ResponseEntity<Long>  deleteById(@PathVariable(value = "id") Long userID) {
        Long deletedUserID =userService.deleteUserById(userID);
        return new ResponseEntity<>(deletedUserID, HttpStatus.OK);
    }

    @GetMapping(UrlMapping.SEARCH_USERS_BY_NAME)
    public ResponseEntity<List<UserDTO>> searchUsersByName(@PathVariable(value = "name") String name){
        List<UserDTO> userDTOList = userService.searchUsersByName(name);
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }



}
