package com.lab.backend.security.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.backend.security.core.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 用户与角色关系DAO
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {
    void myInsert(Map<String, Long> map);
    void myDelete(Map<String, Long> map);
}
