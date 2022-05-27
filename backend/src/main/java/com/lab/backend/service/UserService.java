package com.lab.backend.service;

public interface UserService {

    int changePassword(String oldPassword, String newPassword);
}
