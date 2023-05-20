package com.smile.dao;

import com.github.pagehelper.Page;
import com.smile.pojo.CheckGroup;
import com.smile.pojo.CheckItem;

import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);
    public Page<CheckGroup> selectByCondition(String queryString);
//    public long findCountByCheckItemId(Integer id);
}
