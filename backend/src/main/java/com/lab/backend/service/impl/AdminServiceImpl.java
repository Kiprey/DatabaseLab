package com.lab.backend.service.impl;

import com.lab.backend.repository.StudentDao;
import com.lab.backend.repository.TeacherDao;
import com.lab.backend.security.core.dao.SysUserDao;
import com.lab.backend.security.core.dao.SysUserRoleDao;
import com.lab.backend.security.core.entity.SysUserEntity;
import com.lab.backend.security.core.entity.SysUserRoleEntity;
import com.lab.backend.security.core.service.SysUserRoleService;
import com.lab.backend.security.core.service.SysUserService;
import com.lab.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private StudentDao studentDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 注册管理员
     *
     * @return 0:成功, 1:失败，邀请码无效
     */
    @Override
    public int adminRegister(String username, String password, String code) {
        if (sysUserDao.selectSysUserByUsername(username).size() > 0) {
            return 2;
        }
        //验证邀请码
        if (!Objects.equals(code, "hnu123456"))
            return 1;
        // 注册用户
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(username);
        sysUserEntity.setPassword(bCryptPasswordEncoder.encode(password));
        // 设置用户状态
        sysUserEntity.setStatus("NORMAL");
        sysUserService.save(sysUserEntity);
        // 分配角色
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setRoleId(1L);
        sysUserRoleEntity.setUserId(sysUserEntity.getUserId());
        sysUserRoleService.save(sysUserRoleEntity);
        return 0;
    }

    /**
     * 注册学生用户
     *
     * @return 0:成功, 1:失败，该用户名已存在
     */
    @Override
    public int studentRegister(String username, String password) {
        // 注册用户
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(username);
        sysUserEntity.setPassword(bCryptPasswordEncoder.encode(password));
        // 设置用户状态
        sysUserEntity.setStatus("NORMAL");
        int num = sysUserDao.selectSysUserByUsername(username).size();
        if (num > 0)
            return 1;
        sysUserService.save(sysUserEntity);
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setRoleId(2L);
        sysUserRoleEntity.setUserId(sysUserEntity.getUserId());
        sysUserRoleService.save(sysUserRoleEntity);
        return 0;
    }

    /**
     * 注册教师用户
     *
     * @return 0:成功, 1:失败，该用户名已存在
     */
    @Override
    public int teacherRegister(String username, String password) {
        // 注册用户
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(username);
        sysUserEntity.setPassword(bCryptPasswordEncoder.encode(password));
        // 设置用户状态
        sysUserEntity.setStatus("NORMAL");
        int num = sysUserDao.selectSysUserByUsername(username).size();
        if (num > 0)
            return 1;
        sysUserService.save(sysUserEntity);
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setRoleId(3L);
        sysUserRoleEntity.setUserId(sysUserEntity.getUserId());
        sysUserRoleService.save(sysUserRoleEntity);
        return 0;
    }

    /**
     * 注册普通用户
     *
     * @return 0:成功, 1:失败，该学生不存在, 2:失败，该教师不存在，3:失败，该用户名已存在, 4:失败，没有该角色
     */
    @Override
    public int userRegister(String username, String password, String role) {
        int num = sysUserDao.selectSysUserByUsername(username).size();
        if (num > 0)
            return 3;
        //查看角色
        if (Objects.equals(role, "STUDENT")) {
            int num2 = studentDao.getByID(username).size();
            if (num2 == 0)
                return 1;
            studentRegister(username, password);
            return 0;
        } else if (Objects.equals(role, "TEACHER")) {
            int num3 = teacherDao.getByID(username).size();
            if (num3 == 0)
                return 2;
            teacherRegister(username, password);
            return 0;
        }
        return 4;
    }

    /**
     * 根据用户名查找用户名-权限名
     * 若用户名为空，则查看全部
     */
    @Override
    public Map<Object, Object> selectSysUserRoleByUsername(Map<String,Object> map, int pageIndex, int pageSize) {

        StringBuilder sql = new StringBuilder("SELECT sys_user.username,sys_role.role_name FROM sys_user,sys_role,sys_user_role  WHERE sys_user.user_id=sys_user_role.user_id and sys_role.role_id=sys_user_role.role_id ");
        //给出params
        List<Object> params = new ArrayList<>();
        if (map.get("username")!=null)
        {
            String s = map.get("username").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and username = ?");
                params.add("%" + s + "%");
            }
        }
        sql.append("ORDER BY username");
        //统计个数
        String sql2 = "SELECT count(*) as sum from (" + sql + ") as a;";
        int count = jdbcTemplate.queryForObject(sql2, Integer.class, params.toArray());
        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);

        Map<Object, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("pageIndex", pageIndex);
        response.put("tableData", jdbcTemplate.queryForList(sql.toString(), params.toArray()));
        return response;
    }
    
}
