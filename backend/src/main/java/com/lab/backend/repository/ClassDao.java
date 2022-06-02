package com.lab.backend.repository;

import com.lab.backend.domain.Classes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClassDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param classes 班级实体
     */
    public void insert(Classes classes) {
        String sql="insert into class values (?,?,?)";
        jdbcTemplate.update(sql,
                classes.getClassName(),
                classes.getClassCode(),
                classes.getMajorCode());
    }

    /**
     * 删除
     * @param classCode 班级代码
     */
    public void delete(String classCode) {
        String sql="delete from class where classCode = ?";
        jdbcTemplate.update(sql, classCode);
    }

    /**
     * 更新
     * @param classes 班级实体
     */
    public void update(Classes classes) {
        String sql="UPDATE class SET className=?, MajorCode=? WHERE classCode=?";
        jdbcTemplate.update(sql,
                classes.getClassName(),
                classes.getMajorCode(),
                classes.getClassCode());
    }

    /**
     * 按某一个属性查询
     * @param attribute 要查询的属性
     * @param name 要查询的属性值
     * @return 查询结果
     */
    public List<Classes> getByAttribute(String attribute, String name) {
        String sql="select * from class where "+attribute+" like ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Classes(
                rs.getString("className"),
                rs.getString("classCode"),
                rs.getString("majorCode")
        ),name);
    }
    /**
     * 按code查询
     */
    public List<Classes> getByCode(String code) {
        String sql="select * from class where classCode = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Classes(
                rs.getString("className"),
                rs.getString("classCode"),
                rs.getString("majorCode")
        ),code);
    }

    /**
     * 多条件模糊查询
     * @param classes 学生实体
     * @param majorName 要查询的专业名称
     * @param facultyName 要查询的院系名称
     * @param pageIndex 起始页
     * @param pageSize  每页个数
     * @return result
     */
    public Map<Object, Object> query(Classes classes, String majorName, String facultyName, int pageIndex, int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select major.facultyCode,facultyName,class.majorCode,majorName,classCode,className from class,major,faculty where class.majorCode=major.majorCode and major.facultyCode=faculty.facultyCode");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        String className = classes.getClassName();
        if(className != null && !className.trim().isEmpty()){
            sql.append(" and className like ?");
            params.add("%" +className+ "%");
        }

        String classCode = classes.getClassCode();
        if(classCode != null && !classCode.trim().isEmpty()){
            sql.append(" and classCode like ?");
            params.add("%" +classCode+ "%");
        }

        String majorCode= classes.getMajorCode();
        if(majorCode != null && !majorCode.trim().isEmpty()){
            sql.append(" and class.majorCode like ?");
            params.add("%" +majorCode+ "%");
        }

        if(majorName != null && !majorName.trim().isEmpty()){
            sql.append(" and majorName like ?");
            params.add("%" +majorName+ "%");
        }
        if(facultyName != null && !facultyName.trim().isEmpty()){
            sql.append(" and facultyName like ?");
            params.add("%" +facultyName+ "%");
        }

        //统计个数
        String sql2="SELECT count(*) as sum from ("+ sql +") as a;";
        int count=jdbcTemplate.queryForObject(sql2, Integer.class,params.toArray());
        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex-1)*pageSize);
        params.add(pageSize);



        Map<Object, Object> response=new HashMap<>();
        response.put("total",count);
        response.put("pageIndex",pageIndex);
        response.put("tableData",jdbcTemplate.queryForList(sql.toString(), params.toArray()));

        return response;
    }
}
