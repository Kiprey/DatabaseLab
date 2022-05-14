package com.lab.backend.service;

import com.lab.backend.domain.Teacher;

import java.util.List;

public interface TeacherService {
    int insert(Teacher teacher);

    int delete(String teacherID);
//  int deleteByFaculty(String facultyCode);

    int update(Teacher teacher);

    List<Teacher> getByName(String name);

    List<Teacher> getByID(String ID);

    List<Teacher> getByFaculty(String facultyCode);

    List<Teacher> getList();


}
