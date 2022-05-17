package com.lab.backend.service;

import com.lab.backend.domain.Faculty;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public interface FacultyService {
    boolean insert(Faculty faculty);

    int delete(String facultyCode);

    boolean update(Faculty faculty);

    List<Faculty> getList();

    List<Faculty> getListByName(String name);
}
