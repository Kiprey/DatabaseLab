package com.lab.backend.service.impl;

import com.lab.backend.repository.StudentDao;
import com.lab.backend.repository.TeacherDao;
import com.lab.backend.security.core.dao.SysRoleDao;
import com.lab.backend.security.core.dao.SysUserDao;
import com.lab.backend.security.core.dao.SysUserRoleDao;
import com.lab.backend.security.core.entity.SysUserEntity;
import com.lab.backend.security.core.entity.SysUserRoleEntity;
import com.lab.backend.security.core.service.SysUserRoleService;
import com.lab.backend.security.core.service.SysUserService;
import com.lab.backend.service.AdminService;
import com.lab.backend.utils.SecurityUtil;
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
    private SysRoleDao sysRoleDao;
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
     * 根据用户名查找用户名-角色名
     * 若用户名为空，则查看全部
     */
    @Override
    public Map<Object, Object> selectSysUserRoleByUsername(Map<Object, Object> map, int pageIndex, int pageSize) {

        StringBuilder sql = new StringBuilder("SELECT sys_user.username,sys_role.role_name FROM sys_user,sys_role,sys_user_role  WHERE sys_user.user_id=sys_user_role.user_id and sys_role.role_id=sys_user_role.role_id ");
        //给出params
        List<Object> params = new ArrayList<>();
        if (map.get("username") != null) {
            String s = map.get("username").toString();
            if (s != null && !s.trim().isEmpty()) {
                sql.append(" and username = ?");
                params.add(s);
            }
        }
        sql.append(" ORDER BY username");
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

    /**
     * 撤销某用户对应的某角色
     *
     * @param : username,roleName,superCode
     * @return: 0:成功，1:撤销了该用户唯一的角色，该用户被删除，2:授予的角色为“ADMIN”时,超级权限码不正确，3:该用户并无拥有该角色，无法撤销，4:不能撤销自己的管理员权限
     */
    @Override
    public int deleteRoleByUsername(Map<Object, Object> map) {
        String roleName = map.get("roleName").toString();
        Map<Object, Object> cur = selectSysUserRoleByUsername(map, 1, 3);
        List<Map<String, Object>> list = (List<Map<String, Object>>) cur.get("tableData");
        Map<String, Long> mapId = new HashMap<>();
        Long userId = sysUserDao.selectUserIdByUserName(map.get("username").toString());
        Long roleId = sysRoleDao.selectRoleIdByRoleName(map.get("roleName").toString());
        mapId.put("userId", userId);
        mapId.put("roleId", roleId);
        int flag = 1;
        for (Map<String, Object> stringObjectMap : list) {
            String roleName1 = stringObjectMap.get("role_name").toString();
            if (Objects.equals(roleName, roleName1)) {
                flag = 0;
                break;
            }
        }
        if (flag == 1)
            return 3;
        if (Objects.equals(roleName, "ADMIN")) {
            if (Objects.equals(userId, SecurityUtil.getUserId())) {
                return 4;
            }
            if (map.get("superCode") == null)
                return 2;
            String superCode = map.get("superCode").toString();
            if (!Objects.equals(superCode, "hnu123456")) {
                return 2;
            }
        }
        sysUserRoleDao.myDelete(mapId);
        int total = (int) cur.get("total");
        if (total == 1) {
            sysUserDao.deleteUserByUserId(userId);
            return 1;
        }
        return 0;
    }

    /**
     * 授予教师管理员角色
     *
     * @param : map(username,roleName,superCode)
     * @return: 0:成功，1:该用户已经有该角色，无法授予，2:授予的权限为“ADMIN”时,超级权限码不正确，3:无法授予非管理员角色，4:无法授予学生和管理员额外的角色
     */
    @Override
    public int insertRoleByUsername(Map<Object, Object> map) {
        String roleName = map.get("roleName").toString();
        Long userId = sysUserDao.selectUserIdByUserName(map.get("username").toString());
        Long roleId = sysRoleDao.selectRoleIdByRoleName(map.get("roleName").toString());
        List<Map<String, Object>> list = (List<Map<String, Object>>) selectSysUserRoleByUsername(map, 1, 3).get("tableData");
        for (Map<String, Object> stringObjectMap : list) {
            String roleName1 = stringObjectMap.get("role_name").toString();
            if (Objects.equals(roleName, roleName1)) {
                return 1;
            } else if (Objects.equals(roleName1, "ADMIN") || Objects.equals(roleName1, "STUDENT")) {
                return 4;
            }
        }
        if (Objects.equals(roleName, "ADMIN")) {
            if (map.get("superCode") == null)
                return 2;
            String superCode = map.get("superCode").toString();
            if (!Objects.equals(superCode, "hnu123456")) {
                return 2;
            }
        }
        if (!Objects.equals(roleName, "ADMIN")) {
            return 3;
        }
        Map<String, Long> mapId = new HashMap<>();
        mapId.put("userId", userId);
        mapId.put("roleId", roleId);
        sysUserRoleDao.myInsert(mapId);
        return 0;
    }
}
