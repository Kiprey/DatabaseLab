package com.lab.backend.service;

import com.lab.backend.domain.Teacher;

import java.util.List;

public interface TeacherService {
    int insert(Teacher teacher);

    int delete(String teacherID);
//  int deleteByFaculty(String facultyCode);

    int update(Teacher teacher);

    List<Teacher> query(Teacher teacher);

    List<Teacher> getList();


}
