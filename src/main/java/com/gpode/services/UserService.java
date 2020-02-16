package com.gpode.services;

import com.gpode.Model.User;

public interface UserService {
    public User getUserById(long userId);
    public void updateUser(User user);
    public void insertUser(User user);
}
