package com.lab.backend.repository;

import com.lab.backend.domain.Student;
import com.lab.backend.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * 多条件模糊查询
     *
     * @param map 查询条件
     * @param pageIndex 起始页
     * @param pageSize  每页个数
     * @return result
     */
    public Map<Object, Object> query(Map<String,Object> map, int pageIndex, int pageSize) {
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql = new StringBuilder("select teacherID,teacherName,teacher.facultyCode,facultyName from teacher,faculty where 1=1 and teacher.facultyCode=faculty.facultyCode");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        if (map.get("teacherName")!=null)
        {
            String s = map.get("teacherName").toString();
            if (s != null && !s.trim().isEmpty())
            {
                sql.append(" and teacherName like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("teacherID")!=null)
        {
            String s = map.get("teacherID").toString();
            if (s != null && !s.trim().isEmpty()) {
                sql.append(" and teacherID like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("facultyCode")!=null)
        {
            String s = map.get("facultyCode").toString();
            if (s != null && !s.trim().isEmpty()) {
                sql.append(" and teacher.facultyCode like ?");
                params.add("%" + s + "%");
            }
        }
        if (map.get("facultyName")!=null)
        {
            String s = map.get("facultyName").toString();
            if (s != null && !s.trim().isEmpty()) {
                sql.append(" and facultyName like ?");
                params.add("%" + s + "%");
            }
        }
        //统计个数
        String sql2 = "SELECT count(*) as sum from (" + sql + ") as a;";
        int count = jdbcTemplate.queryForObject(sql2, Integer.class, params.toArray());
        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);

        Map<Object, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("pageIndex", pageIndex);
        response.put("tableData", jdbcTemplate.queryForList(sql.toString(), params.toArray()));
        return response;
    }

    /**
     * 按teacher字段查询
     *
     * @param attribute 字段名
     * @param name      字段的值
     * @return 返回查询结果
     */
    public List<Teacher> getByAttribute(String attribute, String name) {
        String sql = "select * from teacher where " + attribute + "=?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
                        rs.getString("teacherName"),
                        rs.getString("teacherID"),
                        rs.getString("facultyCode"))
                , name);
    }

    /**
     * 列表查看
     */
    public List<Teacher> getList() {
        String sql = "select * from teacher";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
                rs.getString("teacherName"),
                rs.getString("teacherID"),
                rs.getString("facultyCode")
        ));
    }

    /**
     * 按ID查询
     */
    public List<Teacher> getByID(String ID) {
        String sql="select * from teacher where teacherID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Teacher(
                rs.getString("teacherName"),
                rs.getString("teacherID"),
                rs.getString("facultyCode"))
        ,ID);
    }
}
