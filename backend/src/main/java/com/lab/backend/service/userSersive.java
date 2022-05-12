package com.lab.backend.service;

import com.lab.backend.domain.User;

import java.util.List;

public interface userSersive {
    User insert(User user);
    List<User> getList();
    List<User> getByUsername(String username);
    User delete(String username);
}
