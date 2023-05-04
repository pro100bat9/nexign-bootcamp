package com.example.crm.controllers;

import com.example.common.entity.Role;
import com.example.common.entity.Users;
import com.example.common.service.UserService;
import com.example.crm.wrappers.request.DataForRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;
    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody DataForRegister data){
        if(data.getRole().equals(Role.ABONENT.name())) {
            Users users = new Users(data.getLogin(), data.getNumberPhone(), data.getPassword(), Role.ABONENT);
            userService.saveUser(users);
        }
        else if(data.getRole().equals(Role.MANAGER.name())){
            Users users = new Users(data.getLogin(), data.getNumberPhone(), data.getPassword(), Role.MANAGER);
            userService.saveUser(users);
        }
        else{
            return new ResponseEntity<>("role does not exist", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(data.getLogin() + " register successful");
    }
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/")
    public String Hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userService.getUserByLogin(authentication.getName());
        if(users.getRole().equals(Role.MANAGER)) {
            Authentication originalAuthentication = SecurityContextHolder.getContext().getAuthentication();
            List<GrantedAuthority> newAuthorities = new ArrayList<>(originalAuthentication.getAuthorities());
            int index = -1;
            for (int i = 0; i < newAuthorities.size(); i++) {
                if (newAuthorities.get(i).getAuthority().equals("ROLE_ABONENT")) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                newAuthorities.set(index, new SimpleGrantedAuthority("ROLE_MANAGER"));
            }
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(originalAuthentication.getPrincipal(),
                    originalAuthentication.getCredentials(), newAuthorities);
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }
        return "hello " + authentication.getName();
    }

}
