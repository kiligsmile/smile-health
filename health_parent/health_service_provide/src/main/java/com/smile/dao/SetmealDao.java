package com.smile.dao;

import com.github.pagehelper.Page;
import com.smile.pojo.CheckGroup;
import com.smile.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map<String, Integer> map);
    public Page<CheckGroup> selectByCondition(String queryString);
    public List<Setmeal> findAll();
    public Setmeal findById(int id);
}
