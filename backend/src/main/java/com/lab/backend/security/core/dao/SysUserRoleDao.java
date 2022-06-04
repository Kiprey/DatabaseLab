package com.lab.backend.security.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.backend.security.core.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Description 用户与角色关系DAO
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {
    void myInsert(Map<String, Long> map);
    void myDelete(Map<String, Long> map);
}
