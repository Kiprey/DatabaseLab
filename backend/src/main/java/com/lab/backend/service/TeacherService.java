package com.lab.backend.service;

import com.lab.backend.domain.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    int insert(Teacher teacher);

    int delete(String teacherID);
//  int deleteByFaculty(String facultyCode);

    int update(Teacher teacher);

    Map<Object, Object> query(Teacher teacher, int pageIndex, int pageSize);

    List<Teacher> getList();


}
