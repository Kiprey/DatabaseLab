package com.lab.backend.security.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.backend.security.core.entity.SysMenuEntity;
import com.lab.backend.security.core.entity.SysRoleEntity;
import com.lab.backend.security.core.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description 系统用户DAO
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 通过用户ID查询角色集合
     *
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);

    /**
     * 通过用户ID查询权限集合
     *
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<SysMenuEntity> selectSysMenuByUserId(Long userId);

    List<SysUserEntity> selectSysUserByUsername(String username);

    void updatePasswordByUsername(Map<String, Object> map);

    Long selectUserIdByUserName(String username);
}
