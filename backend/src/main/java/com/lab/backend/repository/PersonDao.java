package com.lab.backend.repository;

import com.lab.backend.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class PersonDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入
     * @param person person实体
     * @return person实体
     */
    public Person insert(Person person) {
        String sql="insert into person values (?,?,?,?)";
        jdbcTemplate.update(sql, person.getUsername(), person.getName(), person.getAge(),person.getTeleno());
        return person;
    }

    /**
     * 删除
     */
    public void delete(String username) {
        String sql="delete from person where username = ?";
        jdbcTemplate.update(sql, username);
    }

    /**
     * 更新
     */
    public void update(Person person) {
        String sql="UPDATE person SET name=?, age=?, teleno=? WHERE username=?";
        jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getTeleno(), person.getUsername());
    }

    /**
     * 按username查询
     */
    public List<Person> getByUsername(String username) {
        String sql="select * from person where username=?";
        return jdbcTemplate.query(sql, new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person();
                person.setUsername(rs.getString("username"));
                person.setName(rs.getString("name"));
                person.setAge(rs.getInt("age"));
                person.setTeleno(rs.getString("teleno"));
                return person;
            }
        },username);
    }

    /**
     * 查询person列表
     */
    public List<Person> getList() {
        String sql="select * from person";
        return jdbcTemplate.query(sql, new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person();
                person.setUsername(rs.getString("username"));
                person.setName(rs.getString("name"));
                person.setAge(rs.getInt("age"));
                person.setTeleno(rs.getString("teleno"));
                return person;
            }
        });
    }
}
