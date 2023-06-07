package com.smile.service;

import com.smile.pojo.User;

public interface UserService {
    public User findByUsername(String username);
}
