package com.lab.backend.security.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.backend.security.core.entity.SysMenuEntity;
import com.lab.backend.security.core.entity.SysRoleEntity;
import com.lab.backend.security.core.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户DAO
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 通过用户ID查询角色集合
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);

    /**
     * 通过用户ID查询权限集合
     */
    List<SysMenuEntity> selectSysMenuByUserId(Long userId);

    /**
     * 通过用户名查询用户集合
     */
    List<SysUserEntity> selectSysUserByUsername(String username);

    /**
     * 更新指定用户名的密码
     * map：username,password
     */
    void updatePasswordByUsername(Map<String, Object> map);

    /**
     * 通过用户名查询用户ID
     */
    Long selectUserIdByUserName(String username);

    /**
     * 通过用户ID删除用户
     */
    void deleteUserByUserId(Long userId);
}
