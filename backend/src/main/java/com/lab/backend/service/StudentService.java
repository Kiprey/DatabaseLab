package com.lab.backend.service;
import com.lab.backend.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StudentService {
    int insert(Student student);
    List<Student> getList();
    List<Student> getByUsername(String username);
}
