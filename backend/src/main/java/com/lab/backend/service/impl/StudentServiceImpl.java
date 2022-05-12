package com.lab.backend.service.impl;

import com.lab.backend.domain.Student;
import com.lab.backend.repository.StudentDao;
import com.lab.backend.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;
    @Override
    public int insert(Student student) {
        studentDao.insert(student);
        return 0;
    }

    @Override
    public List<Student> getList() {
        return null;
    }

    @Override
    public List<Student> getByUsername(String username) {
        return null;
    }
}
