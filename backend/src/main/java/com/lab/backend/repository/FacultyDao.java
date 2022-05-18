package com.lab.backend.repository;

import com.lab.backend.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class FacultyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 插入
     * @param faculty 院系实体
     */
    public void insert(Faculty faculty) {
        String sql="insert into faculty values (?,?,?,?)";
        jdbcTemplate.update(sql,
                faculty.getFacultyName(),
                faculty.getFacultyCode(),
                faculty.getFacultyAddress(),
                faculty.getFacultyTeleno());
    }

    /**
     * 删除
     * @param facultyCode 院系代码
     */
    public void delete(String facultyCode) {
        String sql="delete from Faculty where facultyCode = ?";
        jdbcTemplate.update(sql, facultyCode);
    }

    /**
     * 更新
     * @param faculty 院系实体
     */
    public void update(Faculty faculty) {
        String sql="UPDATE faculty SET facultyName=?,facultyAddress=?, facultyTeleno=? WHERE facultyCode=?";
        jdbcTemplate.update(sql,
                faculty.getFacultyName(),
                faculty.getFacultyAddress(),
                faculty.getFacultyTeleno(),
                faculty.getFacultyCode());
    }

    /**
     * 按Code查询
     */
    public List<Faculty> getByCode(String code) {
        String sql="select * from faculty where facultyCode=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Faculty(
                rs.getString("facultyName"),
                rs.getString("facultyCode"),
                rs.getString("facultyAddress"),
                rs.getString("facultyTeleno")
        ),code);
    }

    /**
     * 按某一个属性查询
     * @param attribute 要查询的属性
     * @param name 要查询的属性值
     * @return 查询结果
     */
    public List<Faculty> getByAttribute(String attribute,String name) {
        String sql="select * from faculty where "+attribute+" like ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Faculty(
                rs.getString("facultyName"),
                rs.getString("facultyCode"),
                rs.getString("facultyAddress"),
                rs.getString("facultyTeleno")
        ),name);
    }
    /**
     * 列表查看
     */
    public List<Faculty> getList() {
        String sql="select * from faculty";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Faculty(
                rs.getString("facultyName"),
                rs.getString("facultyCode"),
                rs.getString("facultyAddress"),
                rs.getString("facultyTeleno")
        ));
    }

    /**
     * 多条件模糊查询
     * @param faculty 院系实体
     * @param pageIndex 起始页
     * @param pageSize  每页个数
     * @return result
     */
    public Map<Object, Object> query(Faculty faculty, int pageIndex, int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select * from faculty where 1=1");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        String facultyName = faculty.getFacultyName();
        if(facultyName != null && !facultyName.trim().isEmpty()){
            sql.append(" and facultyName like ?");
            params.add("%" +facultyName+ "%");
        }
        String facultyCode= faculty.getFacultyCode();
        if(facultyCode != null && !facultyCode.trim().isEmpty()){
            sql.append(" and facultyCode like ?");
            params.add("%" +facultyCode+ "%");
        }

        String facultyAddress= faculty.getFacultyAddress();
        if(facultyAddress != null && !facultyAddress.trim().isEmpty()){
            sql.append(" and facultyAddress like ?");
            params.add("%" +facultyAddress+ "%");
        }

        String facultyTeleno= faculty.getFacultyTeleno();
        if(facultyTeleno != null && !facultyTeleno.trim().isEmpty()){
            sql.append(" and facultyTeleno like ?");
            params.add("%" +facultyTeleno+ "%");
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
        response.put("tableData",jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new Faculty(
                rs.getString("facultyName"),
                rs.getString("facultyCode"),
                rs.getString("facultyAddress"),
                rs.getString("facultyTeleno")
        ),params.toArray()));

        return response;
    }
}
