package com.smile.service;

import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.pojo.CheckGroup;
import com.smile.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public void edit(CheckGroup checkGroup,Integer[] checkitemIds);
    public CheckGroup findById(Integer id);
    public List<CheckGroup> findAll();
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    public void deleteById(Integer id);
}
