package com.lab.backend.repository;

import com.lab.backend.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class TeacherDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param teacher 教师实体
     */
    public void insert(Teacher teacher) {
        String sql="insert into Teacher values (?,?,?)";
        jdbcTemplate.update(sql,
                teacher.getTeacherName(),
                teacher.getTeacherID(),
                teacher.getFacultyCode());
    }

    /**
     * 删除
     * @param teacherID 教师编号
     */
    public void delete(String teacherID) {
        String sql="delete from Teacher where teacherID = ?";
        jdbcTemplate.update(sql,teacherID);
    }

    /**
     * 更新
     * @param teacher 教师实体
     */
    public void update(Teacher teacher) {
        String sql="UPDATE Teacher SET teacherName=?,facultyCode=? WHERE teacherID=?";
        jdbcTemplate.update(sql,
                teacher.getTeacherName(),
                teacher.getFacultyCode(),
                teacher.getTeacherID());
    }

    /**
     * 按Name查询
     */
    public List<Teacher> getByName(String name) {
        String sql="select * from Teacher where teacherName=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
                rs.getString("teacherName"),
                rs.getString("teacherID"),
                rs.getString("facultyCode")),name);
    }

    /**
     * 按ID查询
     */
    public List<Teacher> getByID(String ID) {
        String sql="select * from Teacher where teacherID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
                rs.getString("teacherName"),
                rs.getString("teacherID"),
                rs.getString("facultyCode")),ID);
    }

    /**
     * 按院系查询
     */
    public List<Teacher> getByFaculty(String facultyCode) {
        String sql="select * from Teacher where facultyCode=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
                rs.getString("teacherName"),
                rs.getString("teacherID"),
                rs.getString("facultyCode")),facultyCode);
    }

    /**
     * 列表查看
     */
    public List<Teacher> getList() {
        String sql="select * from teacher";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
                rs.getString("teacherName"),
                rs.getString("teacherID"),
                rs.getString("facultyCode")
        ));
    }
}
