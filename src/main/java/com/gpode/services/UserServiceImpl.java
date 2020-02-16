package com.gpode.services;

import com.gpode.Dao.UserDao;
import com.gpode.Model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public User getUserById(long userId) {
        return UserDao.ReadById(userId);
    }

    public void updateUser(User user) {
        UserDao.Update(user);
    }

    public void insertUser(User user) {
        UserDao.Insert(user);
    }
}
