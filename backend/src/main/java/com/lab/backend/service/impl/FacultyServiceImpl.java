package com.lab.backend.service.impl;

import com.lab.backend.domain.Faculty;
import com.lab.backend.repository.FacultyDao;
import com.lab.backend.service.FacultyService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
@Service
public class FacultyServiceImpl  implements FacultyService {
    @Resource
    private FacultyDao facultyDao;
    @Override
    public int insert(Faculty faculty) {
        facultyDao.insert(faculty);
        return 0;
    }

    @Override
    public List<Faculty> getList() {
        return null;
    }
}
