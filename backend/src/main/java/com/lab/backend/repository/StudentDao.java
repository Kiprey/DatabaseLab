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
     * 插入
     * @param student student实体
     * @return student实体
     */
    public Student insert(Student student) {
        String sql="insert into student values (?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                student.getStudentName(),
                student.getStudentID(),
                student.getClassName(),
                student.getIdentifier(),
                student.getDormitory(),
                student.getAddress(),
                student.getTeleno(),
                student.getBirthday(),
                student.getSex(),
                student.getGrade(),
                student.getCompletedCredits());
        return student;
    }

    /**
     * 删除
     */
    public void delete(String studentID) {
        String sql="delete from student where studentID = ?";
        jdbcTemplate.update(sql, studentID);
    }

    /**
     * 更新
     */
    public void update(Student student) {
        String sql="UPDATE student SET " +
                "studentName=?, " +
                "className=?, " +
                "identifier=?, " +
                "dormitory=?, " +
                "address=?," +
                "teleno=?," +
                "teleno=?," +
                "birthday=?," +
                "sex=?," +
                "grade=?," +
                "WHERE studentId=?";
        jdbcTemplate.update(sql,
                student.getStudentName(),
                student.getStudentID(),
                student.getClassName(),
                student.getStudentID(),
                student.getDormitory(),
                student.getAddress(),
                student.getTeleno(),
                student.getBirthday(),
                student.getSex(),
                student.getGrade(),
                student.getCompletedCredits());
    }

    /**
     * 按username查询
     */
    public List<Student> getByUsername(String username) {
        String sql="select * from student where studentName=?";
        return jdbcTemplate.query(sql, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Student(
                        rs.getString("studentName"),
                        rs.getString("studentID"),
                        rs.getString("className"),
                        rs.getString("identifier"),
                        rs.getString("dormitory"),
                        rs.getString("address"),
                        rs.getString("teleno"),
                        rs.getString("birthday"),
                        rs.getString("sex"),
                        rs.getString("grade"),
                        rs.getString("completedCredits")
                        );
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
                return new Student(
                        rs.getString("studentName"),
                        rs.getString("studentID"),
                        rs.getString("className"),
                        rs.getString("identifier"),
                        rs.getString("dormitory"),
                        rs.getString("address"),
                        rs.getString("teleno"),
                        rs.getString("birthday"),
                        rs.getString("sex"),
                        rs.getString("grade"),
                        rs.getString("completedCredits")
                );
            }
        });
    }
}
