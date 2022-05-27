package com.lab.backend.service.impl;

import com.lab.backend.repository.StudentDao;
import com.lab.backend.repository.TeacherDao;
import com.lab.backend.security.core.dao.SysUserDao;
import com.lab.backend.security.core.service.SysUserRoleService;
import com.lab.backend.security.core.service.SysUserService;
import com.lab.backend.security.entity.SelfUserEntity;
import com.lab.backend.security.service.SelfUserDetailsService;
import com.lab.backend.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.lab.backend.utils.SecurityUtil.getUserName;

@Service
public class UserServiceImpl implements UserService {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SelfUserDetailsService selfUserDetailsService;

    /**
     * 当前用户修改密码
     * @return 0:成功, 1:失败，原密码错误
     */
    @Override
    public int changePassword(String oldPassword, String newPassword)
    {
        String userName=getUserName();
        SelfUserEntity userInfo = selfUserDetailsService.loadUserByUsername(userName);
        if (!bCryptPasswordEncoder.matches(oldPassword, userInfo.getPassword())) {
            return 1;
        }
        else
        {
            Map<String, Object> map = new HashMap<>();
            map.put("username",userName);
            map.put("password",bCryptPasswordEncoder.encode(newPassword));
            sysUserDao.updatePasswordByUsername(map);
            return 0;
        }
    }
}
