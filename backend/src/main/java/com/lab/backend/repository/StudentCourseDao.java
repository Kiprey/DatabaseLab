package com.lab.backend.repository;

import com.lab.backend.domain.Faculty;
import com.lab.backend.domain.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class StudentCourseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param studentcourse 一个StudentCourse实体
     */
    public void insert(StudentCourse studentcourse) {
        String sql="insert into studentcourse values (?,?,?)";
        jdbcTemplate.update(sql,
                studentcourse.getCourseClassID(),
                studentcourse.getStudentID(),
                studentcourse.getScore());
    }

    /**
     * 按照主键删除
     * @param courseClassID courseClassID字段的值
     */
    public void delete(String courseClassID) {
        String sql="delete from studentcourse where courseClassID = ?";
        jdbcTemplate.update(sql, courseClassID);
    }

    /**
     * 按照主键更新
     * @param studentcourse 一个studentcourse实体
     */
    public void update(StudentCourse studentcourse) {
        String sql="UPDATE studentcourse SET score = ? where courseClassID = ? and studentID = ?";
        jdbcTemplate.update(sql,
                studentcourse.getCourseClassID(),
                studentcourse.getStudentID(),
                studentcourse.getScore());
    }

    /**
     * 按照字段值查询
     * @param attribute 字段名
     * @param name 字段值
     * @return 返回查询结果
     */
    public List<StudentCourse> getByAttribute(String attribute,String name) {
        String sql="select * from studentcourse where "+attribute+"=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new StudentCourse(
                rs.getString("courseClassID"),
                rs.getString("studentID"),
                rs.getInt("score")),
                name);
    }

    /**
     * 按照主键查询
     * @param code 主键值
     * @return 返回查询结果
     */
    public List<StudentCourse> getByCode(String code) {
        String sql="select * from studentcourse where courseClassID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new StudentCourse(
                rs.getString("courseClassID"),
                rs.getString("studentID"),
                rs.getInt("score")),
                code);
    }

    /**
     * 查询studentcourse表所有数据
     * @return 返回查询结果
     */
    public List<StudentCourse> getList() {
        String sql="select * from studentcourse";
        return jdbcTemplate.query(sql,(rs, rowNum) -> new StudentCourse(
                rs.getString("courseClassID"),
                rs.getString("studentID"),
                rs.getInt("score"))
        );
    }
}
