package com.smile.service;

import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.pojo.CheckItem;

public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public void deleteById(Integer id);
    public void edit(CheckItem checkItem);
    public CheckItem findById(Integer id);
}
