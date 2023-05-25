package com.example.crm.controllers;

import com.example.crm.exception.RoleNotFoundException;
import com.example.crm.services.RegisterService;
import com.example.crm.wrappers.request.AuthenticationRequest;
import com.example.crm.wrappers.request.DataForRegister;
import com.example.crm.wrappers.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JwtRegisterController {
    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody DataForRegister data){
        AuthenticationResponse response;
        try{
            response = registerService.register(data);
        }
        catch (RoleNotFoundException ex){
            return new ResponseEntity<>("role does not exist", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(registerService.authentication(request));
    }
}
