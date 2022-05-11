package com.lab.backend.service.impl;

import com.lab.backend.domain.Person;
import com.lab.backend.domain.User;
import com.lab.backend.repository.PersonDao;
import com.lab.backend.repository.UserDao;
import com.lab.backend.service.PersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonDao personDao;
    @Resource
    private UserDao userDao;
    @Override
    public int insert(Person person) {
        int u=userDao.getByUsername(person.getUsername()).size();
        int p=personDao.getByUsername(person.getUsername()).size();
        if(u!=0){
            if(p!=0){
                personDao.update(person);
                return 0;//该记录已存在person表和user表，更新person表数据
            }
            else {
                personDao.insert(person);
                return 1;//该记录已存在users表，不存在person表，插入person表
            }
        }
        else {
            //不存在user，插入user表,user表默认密码为888888
            userDao.insert(new User(person.getUsername(),"888888"));
            if(p!=0){
                personDao.update(person);
                return 2;//该记录不存在user表，存在person表，插入user表，更新person表数据
            }
            else {
                personDao.insert(person);
                return 3;//该记录不存在users表和person表，插入user表和person表
            }
        }
    }

    @Override
    public List<Person> getList() {
        return personDao.getList();
    }


    @Override
    public List<Person> getByUsername(String username) {
        return personDao.getByUsername(username);
    }
}
