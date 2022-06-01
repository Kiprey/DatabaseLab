package com.lab.backend.service;

public interface AdminService {
    int adminRegister(String username, String password, String code);

    int studentRegister(String username, String password);

    int teacherRegister(String username, String password);

    int userRegister(String username, String password, String role);
}
