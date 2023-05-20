package com.smile.service;

import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.pojo.CheckGroup;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);
    public PageResult pageQuery(QueryPageBean queryPageBean);
}
