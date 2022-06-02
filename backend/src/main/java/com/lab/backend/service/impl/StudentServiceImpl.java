package com.lab.backend.service.impl;

import com.lab.backend.repository.ClassDao;
import com.lab.backend.domain.Student;
import com.lab.backend.repository.StudentDao;
import com.lab.backend.service.StudentService;
import com.lab.backend.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private ClassDao classDao;

    /**
     * 插入
     * @param student 学生实体
     * @return 执行结果
     */
    @Override
    public int insert(Student student) {
        int num=studentDao.getByID(student.getStudentID()).size();
        System.out.println(student.getStudentID());
        System.out.println(student.getClassCode());
        int classNum=classDao.getByCode(student.getClassCode()).size();
        if(num==0){
            if(classNum!=0){
                studentDao.insert(student);
                return 0;//成功插入
            }
            else{
                return 1;//班级不存在，无法插入
            }
        }
        else {
            return 2;//当前学生已存在，无法插入
        }
    }
    /**
     * 删除
     * @param studentCode 学生代码
     * @return int 0成功,1失败
     */
    @Override
    public int delete(String studentCode) {
        int num=studentDao.getByID(studentCode).size();
        if(num!=0) {
            studentDao.delete(studentCode);
            return 0;
        }
        else {
            return 1;//学生不存在，无法删除
        }
    }
    /**
     * 更新
     * @param student 学生实体
     * @return int 0成功,1失败
     */
    @Override
    public int update(Student student) {
        int num=studentDao.getByID(student.getStudentID()).size();
        int classNum=classDao.getByCode(student.getClassCode()).size();
        if(num!=0){
            if(classNum!=0){
                studentDao.update(student);
                return 0;//成功更新
            }
            else{
                return 1;//班级不存在，无法更新
            }
        }
        else {
            return 2;//当前学生不存在，无法更新
        }
    }

    /**
     * 更新
     * @param student 学生实体
     * @return int 0成功,1失败
     */
    @Override
    public int updateByStudent(Student student) {
        student.setStudentID(SecurityUtil.getUserName());
        int num=studentDao.getByID(student.getStudentID()).size();
        if(num!=0){
                studentDao.updateByStudent(student);
                return 0;//成功更新
        }
        else {
            return 1;//当前学生不存在，无法更新
        }
    }
    /**
     * 按code查询
     * @return result info
     */
    @Override
    public List<Map<String, Object>> getInfo(){
        return studentDao.getByID(SecurityUtil.getUserName());
    }

    /**
     * 多条件查询
     * @param student 学生实体
     * @return result list
     */
    @Override
    public Map<Object, Object> query(Student student, String className, String majorName, String facultyName, int pageIndex, int pageSize){
            return studentDao.query(student,className,majorName,facultyName,pageIndex,pageSize);
    }

}
