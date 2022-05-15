package com.lab.backend.repository;

import com.lab.backend.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CourseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param course 课程实体
     */
    public void insert(Course course) {
        String sql="insert into course values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getCourseNature(),
                course.getCourseCategory(),
                course.getCourseID(),
                course.getFacultyCode(),
                course.getCourseHours(),
                course.getCredit());
    }

    /**
     * 按照主键删除
     * @param CourseID CourseID字段
     */
    public void delete(String CourseID) {
        String sql="delete from course where CourseID = ?";
        jdbcTemplate.update(sql, CourseID);
    }

    /**
     * 按照主键更新
     * @param course 课程实体
     */
    public void update(Course course) {
        String sql="UPDATE course SET coursename=?," +
                "courseNature=?, " +
                "courseCategory=?," +
                "facultyCode=?," +
                "courseHours=?," +
                "credit=? WHERE courseID=?";
        jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getCourseNature(),
                course.getCourseCategory(),
                course.getFacultyCode(),
                course.getCourseHours(),
                course.getCredit(),
                course.getCourseID());
    }

    /**
     * 按字段查询
     * @param attribute 字段名
     * @param name 字段值
     * @return 返回查询结果
     */
    public List<Course> getByAttribute(String attribute, String name) {
        String sql="select * from course where " + attribute + "=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Course(
                        rs.getString("coursename"),
                        rs.getString("courseNature"),
                        rs.getString("courseCategory"),
                        rs.getString("courseID"),
                        rs.getString("facultyCode"),
                        rs.getInt("courseHours"),
                        rs.getInt("credit"))
                ,name);
    }

    /**
     * 按courseID字段查询
     * @param code courseID字段的值
     * @return 返回查询结果
     */
    public List<Course> getByCode(String code) {
        String sql="select * from course where courseID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Course(
                        rs.getString("coursename"),
                        rs.getString("courseNature"),
                        rs.getString("courseCategory"),
                        rs.getString("courseID"),
                        rs.getString("facultyCode"),
                        rs.getInt("courseHours"),
                        rs.getInt("credit")),
                code);
    }

    /**
     * 查询course表所有数据
     * @return 返回查询结果
     */
    public List<Course> getList() {
        String sql="select * from course";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Course(
                rs.getString("coursename"),
                rs.getString("courseNature"),
                rs.getString("courseCategory"),
                rs.getString("courseID"),
                rs.getString("facultyCode"),
                rs.getInt("courseHours"),
                rs.getInt("credit"))
        );
    }
}
