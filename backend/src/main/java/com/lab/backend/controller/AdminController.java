package com.lab.backend.controller;

import com.lab.backend.service.AdminService;
import com.lab.backend.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    /**
     * 注册管理员用户
     */
    @PostMapping("/adminRegister")
    public Map<String, Object> adminRegister(@RequestParam String username, @RequestParam String password, @RequestParam String code) {
        if (adminService.adminRegister(username, password, code) == 2) {
            return Result.resultCode("500", "该用户名已存在");
        } else if (adminService.adminRegister(username, password, code) == 1) {
            return Result.resultCode("500", "邀请码不正确");
        } else {
            return Result.resultCode("200", "注册成功");
        }
    }

    /**
     * 注册普通用户
     * 仅限管理员使用
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/userRegister")
    public Map<String, Object> userRegister(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        int res = adminService.userRegister(username, password, role);
        if (res == 0) {
            return Result.resultCode("200", "注册成功");
        } else if (res == 1) {
            return Result.resultCode("1", "该学生不存在");
        } else if (res == 2) {
            return Result.resultCode("2", "该教师不存在");
        } else if (res == 3) {
            return Result.resultCode("3", "该用户名已存在");
        } else {
            return Result.resultCode("4", "不存在该角色");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/selectUserRole")
    public Result<Map<Object, Object>> selectUserRole(@RequestBody Map<Object, Object> map, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Map<Object, Object> response = adminService.selectSysUserRoleByUsername(map, pageIndex, pageSize);
        if ((int) response.get("total") != 0) {
            return Result.success(response, "查询成功");
        } else {
            return Result.error("1", "查询结果为空");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insertRole")
    public Result<Map<Object, Object>> insertRole(@RequestBody Map<Object, Object> map) {
        int response = adminService.insertRoleByUsername(map);
        if (response == 0) {
            return Result.success(map, "成功授予角色");
        } else if (response == 1) {
            return Result.error("1", "该用户已经有该角色，无法授予");
        } else if (response == 2) {
            return Result.error("2", "超级权限码错误，无法授予ADMIN角色");
        } else {
            return Result.error("3", "该角色不存在");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteRole")
    public Result<Map<Object, Object>> deleteRole(@RequestBody Map<Object, Object> map) {
        int response = adminService.deleteRoleByUsername(map);
        if (response == 0) {
            return Result.success(map, "成功撤销角色");
        } else if (response == 1) {
            return Result.success(map, "撤销了该用户唯一的角色，该用户被删除");
        } else if (response == 2) {
            return Result.error("2", "超级权限码错误，无法撤销ADMIN角色");
        } else if (response == 3) {
            return Result.error("3", "该用户没有该角色，无法撤销");
        } else {
            return Result.error("4", "不能撤销自己的管理员角色");
        }
    }
}
