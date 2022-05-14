package com.lab.backend.repository;

import com.lab.backend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增
     */
    public User insert(User user) {
        String sql="insert into users values (?,?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPass());
        return user;
    }

    /**
     * 删除
     */
    public void delete(String username) {
        String sql="delete from users where username = ?";
        jdbcTemplate.update(sql, username);
    }

    /**
     * 更新
     */
    public void update(User user) {
        String sql="UPDATE users SET pass=? WHERE username=?";
        jdbcTemplate.update(sql, user.getPass(), user.getUsername());
    }

    /**
     * 按username查询
     */
    public List<User> getByUsername(String username) {
        String sql="select * from users where username=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPass(rs.getString("pass"));
            return user;
        },username);
    }

    /**
     * 查询user列表
     */
    public List<User> getList() {
        String sql="select * from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPass(rs.getString("pass"));
            return user;
        });
    }
}
