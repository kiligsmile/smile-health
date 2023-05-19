package com.smile.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smile.dao.CheckItemDao;
import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.pojo.CheckItem;
import com.smile.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//检查项服务
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
//    注入DAO对象
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // 完成分页查询，基于mybatis框架提供的分页助手插件
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }

//    根据id删除检查项
    @Override
    public void deleteById(Integer id) {
//        判断当前检查项是否关联检查组
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count>0){
            // 已经关联
            new RuntimeException();
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }
}
