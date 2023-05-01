package com.example.common.service;

import com.example.common.entity.Users;
public interface UserService {
    Users getUserByLogin(String login);
    void saveUser(Users user);
}
