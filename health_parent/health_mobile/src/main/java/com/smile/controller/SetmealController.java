package com.smile.controller;
//套餐管理

import com.alibaba.dubbo.config.annotation.Reference;
import com.smile.constant.MessageConstant;
import com.smile.entity.Result;
import com.smile.pojo.Setmeal;
import com.smile.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
//    查询所有套餐
    @RequestMapping("/getAllSetmeal")
    public Result getAllSetmeal(){
        try {
            List<Setmeal> list= setmealService.findAll();
            return new  Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new  Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    //    根据套餐id查询套餐详情
    @RequestMapping("/findById")
    public Result findById(int id){
        try {
            Setmeal setmeal= setmealService.findById(id);
            return new  Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new  Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
