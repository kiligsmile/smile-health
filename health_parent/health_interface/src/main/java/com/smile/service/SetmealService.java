package com.smile.service;

import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.pojo.CheckGroup;
import com.smile.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public List<Setmeal> findAll();
}
