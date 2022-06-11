package com.lab.backend.service;

import com.lab.backend.domain.Classes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ClassService {
    int insert(Classes classes);

    int delete(String classCode);

    int update(Classes classes);

    Map<Object, Object> query(Classes classes, String majorName, String facultyName, int pageIndex, int pageSize);
}
