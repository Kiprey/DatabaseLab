package com.lab.backend.service;
import com.lab.backend.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentService {
    int insert(Student student);

    int delete(String studentCode);

    int update(Student student);

    int updateByStudent(Student student);

    List<Map<String, Object>> getInfo();

    Map<Object, Object> query(Student student, String className, String majorName, String facultyName, int pageIndex, int pageSize);
}
