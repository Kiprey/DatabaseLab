package com.lab.backend.repository;

import com.lab.backend.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

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
                student.getClassCode(),
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
                "classCode=?, " +
                "identifier=?, " +
                "dormitory=?, " +
                "address=?," +
                "teleno=?," +
                "birthday=?," +
                "sex=?," +
                "grade=?," +
                "completedCredits=?" +
                "WHERE studentId=?";
        jdbcTemplate.update(sql,
                student.getStudentName(),
                student.getClassCode(),
                student.getStudentID(),
                student.getDormitory(),
                student.getAddress(),
                student.getTeleno(),
                student.getBirthday(),
                student.getSex(),
                student.getGrade(),
                student.getCompletedCredits(),
                student.getStudentID());
    }

    /**
     * 按某一个属性查询
     * @param attribute 要查询的属性
     * @param value 要查询的属性值
     * @return 查询结果
     */
    public List<Student> getByAttribute(String attribute, String value) {
        String sql="select * from student where "+attribute+" like ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Student(
                rs.getString("studentName"),
                rs.getString("studentID"),
                rs.getString("classCode"),
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
                rs.getString("classCode"),
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
                rs.getString("classCode"),
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

    /**
     * 多条件模糊查询
     * @param student 学生实体
     * @param pageIndex 起始页
     * @param pageSize  每页个数
     * @return result
     */
    public Map<Object, Object> query(Student student,int pageIndex,int pageSize){
        //给出sql模板,为了便于后面添加sql语句
        StringBuilder sql =new StringBuilder("select * from student where 1=1");
        //给出params
        List<Object> params = new ArrayList<>();
        //构造查询语句
        String studentID = student.getStudentID();
        if(studentID != null && !studentID.trim().isEmpty()){
            sql.append(" and studentID like ?");
            params.add("%" +studentID+ "%");
        }
        String studentName= student.getStudentName();
        if(studentName != null && !studentName.trim().isEmpty()){
            sql.append(" and studentName like ?");
            params.add("%" +studentName+ "%");
        }

        String classCode= student.getClassCode();
        if(classCode != null && !classCode.trim().isEmpty()){
            sql.append(" and classCode like ?");
            params.add("%" +classCode+ "%");
        }

        String identifier= student.getIdentifier();
        if(identifier != null && !identifier.trim().isEmpty()){
            sql.append(" and identifier like ?");
            params.add("%" +identifier+ "%");
        }
        String dormitory= student.getDormitory();
        if(dormitory != null && !dormitory.trim().isEmpty()){
            sql.append(" and dormitory like ?");
            params.add("%" +dormitory+ "%");
        }
        String address= student.getAddress();
        if(address != null && !address.trim().isEmpty()){
            sql.append(" and address like ?");
            params.add("%" +address+ "%");
        }
        String teleno= student.getTeleno();
        if(teleno != null && !teleno.trim().isEmpty()){
            sql.append(" and teleno like ?");
            params.add("%" +teleno+ "%");
        }
        String birthday= student.getBirthday();
        if(birthday != null && !birthday.trim().isEmpty()){
            sql.append(" and birthday like ?");
            params.add("%" +birthday+ "%");
        }
        String sex= student.getSex();
        if(sex != null && !sex.trim().isEmpty()){
            sql.append(" and sex like ?");
            params.add("%" +sex+ "%");
        }
        String grade= student.getGrade();
        if(grade != null && !grade.trim().isEmpty()){
            sql.append(" and grade like ?");
            params.add("%" +grade+ "%");
        }
        String completedCredits= student.getCompletedCredits();
        if(completedCredits != null && !completedCredits.trim().isEmpty()){
            sql.append(" and completedCredits like ?");
            params.add("%" +completedCredits+ "%");
        }

        //添加页数条目限制
        sql.append(" limit ?,?");
        params.add((pageIndex-1)*30);
        params.add(pageSize);
        //统计个数
        String sql2="SELECT count(*) as sum from ("+ sql +") as a;";
        int count=jdbcTemplate.queryForObject(sql2, Integer.class,params.toArray());

        Map<Object, Object> response=new HashMap<>();
        response.put("total",count);
        response.put("pageIndex",pageIndex);
        response.put("tableData",jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new Student(
                rs.getString("studentName"),
                rs.getString("studentID"),
                rs.getString("classCode"),
                rs.getString("identifier"),
                rs.getString("dormitory"),
                rs.getString("address"),
                rs.getString("teleno"),
                rs.getString("birthday"),
                rs.getString("sex"),
                rs.getString("grade"),
                rs.getString("completedCredits")
        ),params.toArray()));

        return response;
    }
}
