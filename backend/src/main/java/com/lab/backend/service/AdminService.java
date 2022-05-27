package com.lab.backend.service;

public interface AdminService {
    int adminRegister(String username, String password, String code);

    int userRegister(String username, String password, String role);
}
