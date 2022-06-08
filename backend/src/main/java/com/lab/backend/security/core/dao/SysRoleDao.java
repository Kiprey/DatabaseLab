package com.lab.backend.security.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.backend.security.core.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色DAO
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    /**
     * 根据角色名查找角色ID
     */
    Long selectRoleIdByRoleName(String username);
}