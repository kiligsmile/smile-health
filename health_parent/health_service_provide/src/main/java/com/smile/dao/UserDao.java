package com.smile.dao;

import com.smile.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
