package com.lab.backend.repository;

import com.lab.backend.domain.Classes;
import com.lab.backend.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ClassDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param classes 班级实体
     */
    public void insert(Classes classes) {
        String sql="insert into class values (?,?)";
        jdbcTemplate.update(sql,
                classes.getClassName(),
                classes.getMajorCode());
    }

    /**
     * 删除
     * @param classCode 班级代码
     */
    public void delete(String classCode) {
        String sql="delete from class where className = ?";
        jdbcTemplate.update(sql, classCode);
    }

    /**
     * 更新
     * @param classes 班级实体
     */
    public void update(Classes classes) {
        String sql="UPDATE class SET MajorCode=? WHERE className=?";
        jdbcTemplate.update(sql,
                classes.getMajorCode(),
                classes.getClassName());
    }

    /**
     * 按某一个属性查询
     * @param attribute 要查询的属性
     * @param name 要查询的属性值
     * @return 查询结果
     */
    public List<Classes> getByAttribute(String attribute, String name) {
        String sql="select * from class where "+attribute+"=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Classes(
                rs.getString("className"),
                rs.getString("majorCode")
        ),name);
    }
    /**
     * 按name查询
     */
    public List<Classes> getByName(String name) {
        String sql="select * from class where className=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Classes(
                rs.getString("className"),
                rs.getString("majorCode")
        ),name);
    }
    /**
     * 列表查看
     */
    public List<Classes> getList() {
        String sql="select * from class";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Classes(
                rs.getString("className"),
                rs.getString("majorCode")
        ));
    }
}
