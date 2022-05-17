package com.lab.backend.repository;

import com.lab.backend.domain.Major;
import com.lab.backend.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                "grade=? " +
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
     * 按某一个属性查询
     * @param attribute 要查询的属性
     * @param value 要查询的属性值
     * @return 查询结果
     */
    public List<Student> getByAttribute(String attribute, String value) {
        String sql="select * from student where "+attribute+"=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Student(
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
        ),value);
    }

    /**
     * 按Code查询
     */
    public List<Student> getByID(String ID) {
        String sql="select * from student where studentID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Student(
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
        ),ID);
    }
    /**
     * 查询student列表
     */
    public List<Student> getList() {
        String sql="select * from student";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Student(
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
        ));
    }
}
