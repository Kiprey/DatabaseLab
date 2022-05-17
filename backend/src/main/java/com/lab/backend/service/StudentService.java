package com.lab.backend.service;
import com.lab.backend.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StudentService {
    int insert(Student student);

    int delete(String studentCode);

    int update(Student student);

    List<Student> getList();
    List<Student> getListByName(String name);
    List<Student> getListByClassName(String className);
}
