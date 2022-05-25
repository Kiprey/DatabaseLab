package com.lab.backend.repository;

import com.lab.backend.domain.Course;
import com.lab.backend.domain.Faculty;
import com.lab.backend.domain.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public void delete(String courseClassID,String studentID) {
        String sql="delete from studentcourse where courseClassID = ? and studentID = ?";
        jdbcTemplate.update(sql, courseClassID,studentID);
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

    /**
     * 多条件模糊查询
     * @param studentCourse 学生选课
     * @param pageIndex 起始页
     * @param pageSize 大小
     * @return 查询结果
     */
    public Map<Object, Object> query(StudentCourse studentCourse, int pageIndex, int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select * from studentcourse where 1=1");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        String courseClassID = studentCourse.getCourseClassID();
        if(courseClassID != null && !courseClassID.trim().isEmpty()){
            sql.append(" and courseClassID like ?");
            params.add("%" +courseClassID+ "%");
        }
        String studentID= studentCourse.getStudentID();
        if(studentID != null && !studentID.trim().isEmpty()){
            sql.append(" and studentID like ?");
            params.add("%" +studentID+ "%");
        }

        Integer score= studentCourse.getScore();
        if(score != null){
            sql.append(" and score like ?");
            params.add("%" +score+ "%");
        }

        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex-1)*30);
        params.add(pageSize);
        //统计个数
        String sql2="SELECT count(*) as sum from ("+ sql +") as a;";
        System.out.println(sql2);
        int count=jdbcTemplate.queryForObject(sql2, Integer.class,params.toArray());

        Map<Object, Object> response=new HashMap<>();
        response.put("total",count);
        response.put("pageIndex",pageIndex);
        response.put("tableData",jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new StudentCourse(
                rs.getString("courseClassID"),
                rs.getString("studentID"),
                rs.getInt("score")
        ),params.toArray()));

        return response;
    }
}
