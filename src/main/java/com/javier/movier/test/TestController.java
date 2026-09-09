package com.javier.movier.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/time")
    public ResponseEntity<?> getTime(){
        return ResponseEntity.ok(LocalDateTime.now());
    }
}
