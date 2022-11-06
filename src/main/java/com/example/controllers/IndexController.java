package com.example.controllers;

import com.example.UrlMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(UrlMapping.API_PATH)
public class IndexController {

    @GetMapping(UrlMapping.ALL)
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("Energy Utility Platform Application is running...", HttpStatus.OK);
    }

}

