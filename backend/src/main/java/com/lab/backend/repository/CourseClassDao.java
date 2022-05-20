package com.lab.backend.repository;

import com.lab.backend.domain.CourseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class CourseClassDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     *
     * @param courseClass 课程班级实体
     */
    public void insert(CourseClass courseClass) {
        String sql = "insert into CourseClass values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                courseClass.getCourseClassID(),
                courseClass.getCourseID(),
                courseClass.getTeacherID(),
                courseClass.getCourseClassTime(),
                courseClass.getCourseClassAddress(),
                courseClass.getCourseClassWeek());
    }

    /**
     * 删除
     *
     * @param courseClassID 开课号
     */
    public void delete(String courseClassID) {
        String sql = "delete from CourseClass where courseClassID = ?";
        jdbcTemplate.update(sql, courseClassID);
    }

    /**
     * 更新
     *
     * @param courseClass 课程班级实体
     */
    public void update(CourseClass courseClass) {
        String sql = "UPDATE CouseClass SET courseID=?,teacherID=?,coureClassTime=?,coureClassAddress=?,courseClassWeek=? WHERE coureClassID=?";
        jdbcTemplate.update(sql,
                courseClass.getCourseID(),
                courseClass.getTeacherID(),
                courseClass.getCourseClassTime(),
                courseClass.getCourseClassAddress(),
                courseClass.getCourseClassWeek(),
                courseClass.getCourseClassID());
    }

    /**
     * 多条件查询
     *
     * @param courseClass 课程班级实体
     */
    public List<CourseClass> query(CourseClass courseClass) {
        String sql="select * from CourseClass where";
        boolean addAnd = false;
        if(courseClass.getCourseClassID()!=null)
        {
            if (addAnd) sql += " and";
            else addAnd = true;
            sql+=" courseClassID="+courseClass.getCourseClassID();
        }
        if(courseClass.getCourseID()!=null)
        {
            if (addAnd) sql += " and";
            else addAnd = true;
            sql+=" courseID="+courseClass.getCourseID();
        }
        if(courseClass.getTeacherID()!=null)
        {
            if (addAnd) sql += " and";
            else addAnd = true;
            sql+=" teacherID="+courseClass.getTeacherID();
        }
        if(courseClass.getCourseClassTime()!=null)
        {
            if (addAnd) sql += " and";
            else addAnd = true;
            sql+=" courseClassTime="+courseClass.getCourseClassTime();
        }
        if(courseClass.getCourseClassAddress()!=null)
        {
            if (addAnd) sql += " and";
            else addAnd = true;
            sql+=" courseClassAddress="+courseClass.getCourseClassAddress();
        }
        if(courseClass.getCourseClassWeek()!=null)
        {
            if (addAnd) sql += " and";
            else addAnd = true;
            sql+=" courseClassWeek="+courseClass.getCourseClassWeek();
        }
        return jdbcTemplate.query(sql, new RowMapper<CourseClass>() {
            @Override
            public CourseClass mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CourseClass(
                    rs.getString("courseClassID"),
                    rs.getString("courseID"),
                    rs.getString("teacherID"),
                    rs.getString("courseClassTime"),
                    rs.getString("courseClassAddress"),
                    rs.getString("courseClassWeek"));
            }
        });
    }

    /**
     * 列表查看（全部查询）
     */
    public List<CourseClass> getList() {
        String sql = "select * from CourseClass";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CourseClass(
                rs.getString("courseClassID"),
                rs.getString("courseID"),
                rs.getString("teacherID"),
                rs.getString("courseClassTime"),
                rs.getString("courseClassAddress"),
                rs.getString("courseClassWeek")
        ));
    }
}


