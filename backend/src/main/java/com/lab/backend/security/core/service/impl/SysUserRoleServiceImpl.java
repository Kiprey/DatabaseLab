package com.lab.backend.security.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lab.backend.security.core.entity.SysUserRoleEntity;
import com.lab.backend.security.core.dao.SysUserRoleDao;
import com.lab.backend.security.core.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @Description 用户与角色业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

}