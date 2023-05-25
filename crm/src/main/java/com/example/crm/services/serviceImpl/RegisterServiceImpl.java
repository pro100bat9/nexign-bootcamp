package com.example.crm.services.serviceImpl;

import com.example.common.entity.Role;
import com.example.common.entity.Users;
import com.example.common.service.serviceImpl.UserServiceImpl;
import com.example.crm.config.JwtService;
import com.example.crm.exception.RoleNotFoundException;
import com.example.crm.services.RegisterService;
import com.example.crm.wrappers.request.AuthenticationRequest;
import com.example.crm.wrappers.request.DataForRegister;
import com.example.crm.wrappers.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(DataForRegister data){
        for(Role role : Role.values()){
            if(role.name().equals(data.getRole())){
                Users user = Users.builder()
                    .login(data.getLogin())
                    .phoneNumber(data.getNumberPhone())
                    .password(data.getPassword())
                    .role(role)
                    .build();
            userService.saveUser(user);
            var token = jwtService.generateToken(user);
            return AuthenticationResponse
                    .builder()
                    .token(token)
                    .build();
            }
        }
        throw new RoleNotFoundException("role" + data.getRole() + " does not exist");
    }

    public AuthenticationResponse authentication(AuthenticationRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        } catch (DisabledException | BadCredentialsException e) {
            e.printStackTrace();
        }
        var user = userService.getUserByLogin(request.getLogin());
        var token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }
}
