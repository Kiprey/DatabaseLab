package com.lab.backend.repository;

import com.lab.backend.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class FacultyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 插入
     * @param faculty 院系实体
     */
    public void insert(Faculty faculty) {
        String sql="insert into faculty values (?,?,?,?)";
        jdbcTemplate.update(sql,
                faculty.getFacultyName(),
                faculty.getFacultyCode(),
                faculty.getFacultyAddress(),
                faculty.getFacultyTeleno());
    }

    /**
     * 删除
     * @param facultyCode 院系代码
     */
    public void delete(String facultyCode) {
        String sql="delete from Faculty where facultyCode = ?";
        jdbcTemplate.update(sql, facultyCode);
    }

    /**
     * 更新
     * @param faculty 院系实体
     */
    public void update(Faculty faculty) {
        String sql="UPDATE faculty SET facultyName=?,facultyAddress=?, facultyTeleno=? WHERE facultyCode=?";
        jdbcTemplate.update(sql,
                faculty.getFacultyName(),
                faculty.getFacultyAddress(),
                faculty.getFacultyTeleno(),
                faculty.getFacultyCode());
    }

    /**
     * 按Code查询
     */
    public List<Faculty> getByCode(String code) {
        String sql="select * from faculty where facultyCode=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Faculty(
                rs.getString("facultyName"),
                rs.getString("facultyCode"),
                rs.getString("facultyAddress"),
                rs.getString("facultyTeleno")
        ),code);
    }

    /**
     * 按某一个属性查询
     * @param attribute 要查询的属性
     * @param name 要查询的属性值
     * @return 查询结果
     */
    public List<Faculty> getByAttribute(String attribute,String name) {
        String sql="select * from faculty where "+attribute+"=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Faculty(
                rs.getString("facultyName"),
                rs.getString("facultyCode"),
                rs.getString("facultyAddress"),
                rs.getString("facultyTeleno")
        ),name);
    }
    /**
     * 列表查看
     */
    public List<Faculty> getList() {
        String sql="select * from faculty";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Faculty(
                rs.getString("facultyName"),
                rs.getString("facultyCode"),
                rs.getString("facultyAddress"),
                rs.getString("facultyTeleno")
        ));
    }
}
