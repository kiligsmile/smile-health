package com.smile.dao;

import com.github.pagehelper.Page;
import com.smile.pojo.CheckGroup;
import com.smile.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);
    public Page<CheckGroup> selectByCondition(String queryString);
    public void edit(CheckGroup checkGroup);
    public void deleteAssociation(Integer id);
    public CheckGroup findById(Integer id);
    public List<CheckGroup> findAll();
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    public void deleteById(Integer id);
}
