package com.lab.backend.service;

import com.lab.backend.domain.Faculty;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public interface FacultyService {
    int insert(Faculty faculty);
    List<Faculty> getList();
}
