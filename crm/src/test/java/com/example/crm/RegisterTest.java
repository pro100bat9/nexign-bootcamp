package com.example.crm;

import com.example.common.entity.Role;
import com.example.common.entity.Users;
import com.example.common.service.UserService;
import com.example.crm.wrappers.request.DataForRegister;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterTest {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegister() {
        // given
        String login = "testuser";
        String password = "testpassword";
        String phoneNumber = "1234567890";
        String roles = "MANAGER";
//        DataForRegister data = new DataForRegister(password, login, phoneNumber, role);
        for(Role role : Role.values()){
            if(role.name().equals(roles)) {
                Users user = Users.builder()
                        .login(login)
                        .phoneNumber(phoneNumber)
                        .password(password)
                        .role(role)
                        .build();
                userService.saveUser(user);
            }
        }
        // then
        Users user = userService.getUserByLogin(login);
        assertNotNull(user);
        Boolean passwordEncode = passwordEncoder.matches(password, user.getPassword());
        assertTrue(passwordEncode);
    }
}
