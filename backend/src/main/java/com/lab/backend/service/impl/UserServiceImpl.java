package com.lab.backend.service.impl;

import com.lab.backend.security.core.dao.SysUserDao;
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
    public int changePassword(Map<String, String> map)
    {
        String oldPassword=map.get("oldPassword");
        String newPassword=map.get("newPassword");
        String userName=getUserName();
        SelfUserEntity userInfo = selfUserDetailsService.loadUserByUsername(userName);
        if (!bCryptPasswordEncoder.matches(oldPassword, userInfo.getPassword())) {
            return 1;
        }
        else
        {
            Map<String, Object> map2 = new HashMap<>();
            map2.put("username",userName);
            map2.put("password",bCryptPasswordEncoder.encode(newPassword));
            sysUserDao.updatePasswordByUsername(map2);
            return 0;
        }
    }
}
