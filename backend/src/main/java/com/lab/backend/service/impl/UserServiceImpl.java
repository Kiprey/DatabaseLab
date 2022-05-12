package com.lab.backend.service.impl;

import com.lab.backend.domain.User;
import com.lab.backend.repository.PersonDao;
import com.lab.backend.repository.UserDao;
import com.lab.backend.service.userSersive;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserServiceImpl implements userSersive {
    @Resource
    private PersonDao personDao;
    @Resource
    private UserDao userDao;

    @Override
    public User insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public List<User> getList() {
        return userDao.getList();
    }

    @Override
    public List<User> getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User delete(String username) {
        if(userDao.getByUsername(username).size()!=0){
            userDao.delete(username);
            personDao.delete(username);
            return new User(username,"******");
        }
        else return null;
    }
}
