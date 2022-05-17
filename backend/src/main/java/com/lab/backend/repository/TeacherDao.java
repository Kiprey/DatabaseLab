package com.lab.backend.repository;

import com.lab.backend.domain.CourseClass;
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
     *
     * @param teacher 教师实体
     */
    public void insert(Teacher teacher) {
        String sql = "insert into Teacher values (?,?,?)";
        jdbcTemplate.update(sql,
                teacher.getTeacherName(),
                teacher.getTeacherID(),
                teacher.getFacultyCode());
    }

    /**
     * 删除
     *
     * @param teacherID 教师编号
     */
    public void delete(String teacherID) {
        String sql = "delete from Teacher where teacherID = ?";
        jdbcTemplate.update(sql, teacherID);
    }

    /**
     * 更新
     *
     * @param teacher 教师实体
     */
    public void update(Teacher teacher) {
        String sql = "UPDATE Teacher SET teacherName=?,facultyCode=? WHERE teacherID=?";
        jdbcTemplate.update(sql,
                teacher.getTeacherName(),
                teacher.getFacultyCode(),
                teacher.getTeacherID());
    }

    /**
     * 多条件查询
     *
     * @param teacher 教师实体
     */
    public List<Teacher> query(Teacher teacher) {
        String sql = "select * from Teacher where 1";
        if (teacher.getTeacherName() != null) {
            sql += " and teacherName=" + teacher.getTeacherName();

        }
        if (teacher.getTeacherID() != null) {
            sql += " and teacherID=" + teacher.getTeacherID();
        }
        if (teacher.getFacultyCode() != null) {
            sql += " and facultyCode=" + teacher.getFacultyCode();
        }
        return jdbcTemplate.query(sql, new RowMapper<Teacher>() {
            @Override
            public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Teacher(
                        rs.getString("teacherName"),
                        rs.getString("teacherID"),
                        rs.getString("facultyCode"));
            }
        });
    }

//    /**
//     * 列表查看
//     */
//    public List<Teacher> getList() {
//        String sql = "select * from teacher";
//        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
//                rs.getString("teacherName"),
//                rs.getString("teacherID"),
//                rs.getString("facultyCode")
//        ));
//    }
}
