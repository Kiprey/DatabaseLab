package com.lab.backend.repository;

import com.lab.backend.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class StudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增
     */
    public Student insert(Student student) {
        String sql="insert into student values (?,?,?)";
        jdbcTemplate.update(sql, student.getName(),student.getSno(),student.getCno());
        return student;
    }

    /**
     * 删除
     */
    public void delete(String name) {
        String sql="delete from student where name = ?";
        jdbcTemplate.update(sql, name);
    }

    /**
     * 更新
     */
    public void update(Student student) {
        String sql="UPDATE student SET name=?, sno=?, cno=? WHERE name=?";
        jdbcTemplate.update(sql, student.getName(),student.getCno(),student.getSno(),student.getName());
    }

    /**
     * 按username查询
     */
    public List<Student> getByUsername(String username) {
        String sql="select * from student where username=?";
        return jdbcTemplate.query(sql, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = new Student();
                student.setName(rs.getString("姓名"));
                student.setCno(rs.getString("班级名称"));
                student.setSno(rs.getString("学生学号"));
                return student;
            }
        },username);
    }

    /**
     * 查询student列表
     */
    public List<Student> getList() {
        String sql="select * from student";
        return jdbcTemplate.query(sql, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = new Student();
                student.setName(rs.getString("姓名"));
                student.setSno(rs.getString("学生学号"));
                student.setCno(rs.getString("班级名称"));
                return student;
            }
        });
    }
}
