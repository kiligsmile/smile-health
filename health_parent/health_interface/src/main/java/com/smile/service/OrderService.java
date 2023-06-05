package com.smile.service;

import com.smile.entity.Result;

import java.util.Map;

public interface OrderService {
    public Result order(Map map) throws Exception;
    public Map findById(Integer id);
}
