package com.royal.HotelRoomBooking.controllers;

import com.royal.HotelRoomBooking.dtos.LoginRequest;
import com.royal.HotelRoomBooking.dtos.RegistrationRequest;
import com.royal.HotelRoomBooking.dtos.Response;
import com.royal.HotelRoomBooking.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid RegistrationRequest request){
        return ResponseEntity.ok(userService.registerUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(userService.loginUser(request));
    }
}
