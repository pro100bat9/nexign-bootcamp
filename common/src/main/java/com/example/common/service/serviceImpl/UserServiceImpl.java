package com.example.common.service.serviceImpl;

import com.example.common.entity.Users;
import com.example.common.repository.UserRepository;
import com.example.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    public Users getUserByLogin(String login){
        return userRepository.findUsersByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with this login not found" + login));
    }

    @Override
    public void saveUser(Users user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPass = user.getPassword();
        user.setPassword(passwordEncoder.encode(oldPass));
        userRepository.save(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = userRepository.findUsersByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User with this login not found" + username));
//        if(user == null){
//            throw new UsernameNotFoundException("user not found:"+username);
//        }
//        UserDetails userDetails = User.builder()
//                .username(user.getLogin())
//                .password(user.getPassword())
//                .roles("ABONENT")
//                .build();
//        return userDetails;
//    }
}
