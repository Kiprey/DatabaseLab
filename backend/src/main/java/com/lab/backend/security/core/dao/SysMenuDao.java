package com.lab.backend.security.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.backend.security.core.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限DAO
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

}