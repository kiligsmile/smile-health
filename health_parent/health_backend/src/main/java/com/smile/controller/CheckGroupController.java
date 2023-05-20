package com.smile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.smile.constant.MessageConstant;
import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.entity.Result;
import com.smile.pojo.CheckGroup;
import com.smile.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//检查组管理
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
//    新增检查组
    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();;
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //    检查项分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.pageQuery(queryPageBean);
        return pageResult;
    }
}
