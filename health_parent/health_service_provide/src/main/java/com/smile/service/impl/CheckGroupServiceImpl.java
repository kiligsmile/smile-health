package com.smile.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smile.dao.CheckGroupDao;
import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.pojo.CheckGroup;
import com.smile.pojo.CheckItem;
import com.smile.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
//    新增检查组，同时让检查组关联检查项
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组合，同时需要设置检查组合和检查项的关联关系
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        if(checkitemIds!=null&&checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map=new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // 完成分页查询，基于mybatis框架提供的分页助手插件
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total,rows);
    }
}
