package com.smile.controller;

//检查项管理

import com.alibaba.dubbo.config.annotation.Reference;
import com.smile.constant.MessageConstant;
import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.entity.Result;
import com.smile.pojo.CheckItem;
import com.smile.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController  {

    @Reference // 查找服务
    private CheckItemService checkItemService;


//    新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            System.out.println("---------------------");
            e.printStackTrace();
            System.out.println("---------------------");
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS  );
    }

    //    检查项分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    // 删除检查项
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS  );
    }

    // 编辑检查项
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS  );
    }

    // 编辑检查项
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkItem= checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
