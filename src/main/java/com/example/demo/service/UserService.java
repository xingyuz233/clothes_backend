package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> getUsers(String username);

}
