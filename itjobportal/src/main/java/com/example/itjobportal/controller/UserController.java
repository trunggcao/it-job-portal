package com.example.itjobportal.controller;

import com.example.itjobportal.dto.AuthDTO;
import com.example.itjobportal.dto.UserDTO;
import com.example.itjobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
        UserDTO registerUser = userService.registerUser(userDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token){
        boolean isActivated = userService.activeUser(token);
        if (isActivated){
            return ResponseEntity.ok("Profile activated success.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ativation token not found or already used.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDTO){
        try{
            if (!userService.isAccountActive(authDTO.getEmail())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "message","Account is not active. Please activate your account."));
            }
            Map<String, Object> response = userService.authenticateAndGenerateToke(authDTO);
            return  ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
}
