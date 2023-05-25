package com.smile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.smile.constant.MessageConstant;
import com.smile.constant.RedisConstant;
import com.smile.entity.PageResult;
import com.smile.entity.QueryPageBean;
import com.smile.entity.Result;
import com.smile.pojo.CheckGroup;
import com.smile.pojo.Setmeal;
import com.smile.service.CheckGroupService;
import com.smile.service.SetmealService;
import com.smile.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

//体检套餐管理
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

//    使用JedisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile);
//        获取原始文件名，以得到图片后缀
        String originalFilename = imgFile.getOriginalFilename();
        int index=originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1); // .jpg
        String fileName= UUID.randomUUID().toString()+extention; // 36的字符串
        try {
//            将文件上传至七牛云服务器
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        }catch (IOException e){
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }

    // 新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();;
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //    检查项分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.pageQuery(queryPageBean);
        return pageResult;
    }
}
