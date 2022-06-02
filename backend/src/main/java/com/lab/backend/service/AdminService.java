package com.lab.backend.service;

import java.util.List;
import java.util.Map;

public interface AdminService {
    int adminRegister(String username, String password, String code);

    int studentRegister(String username, String password);

    int teacherRegister(String username, String password);

    int userRegister(String username, String password, String role);


    Map<Object, Object> selectSysUserRoleByUsername(Map<Object, Object> map, int pageIndex, int pageSize);

    int deleteRoleByUsername(Map<Object, Object> map);

    int insertRoleByUsername(Map<Object, Object> map);
}
