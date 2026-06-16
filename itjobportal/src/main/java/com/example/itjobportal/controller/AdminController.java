package com.example.itjobportal.controller;

import com.example.itjobportal.dto.UserDTO;
import com.example.itjobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUserByNameOrEmail(@RequestParam String keyword){
        List<UserDTO> users = userService.getUserByNameOrEmail(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
