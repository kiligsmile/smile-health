package com.smile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.smile.constant.MessageConstant;
import com.smile.constant.RedisMessageConstant;
import com.smile.entity.Result;
import com.smile.pojo.Order;
import com.smile.service.OrderService;
import com.smile.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

//体检预约
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;
//    在线体检预约
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        String telephone = String.valueOf(map.get("telephone"));
//        从redis中获取保存的验证码
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = String.valueOf(map.get("validateCode"));
        System.out.println("+++++++++++");
        System.out.println("validateCode"+validateCode);
        System.out.println("validateCodeInRedis"+validateCodeInRedis);
        System.out.println("+++++++++++");
//        将用户输入的验证码和redis中保存的验证码进行对比
        if(validateCodeInRedis !=null&&validateCode!=null&&validateCode.equals(validateCodeInRedis)){
            //        比对正确，调用服务完成预约业务处理
            map.put("orderType", Order.ORDERTYPE_WEIXIN);  // 设置预约类型：（微信预约、电话预约）
            Result result =null;
            try{
                result = orderService.order(map);  // 通过Dobbo远程调用服务实现在线预约业务处理
            }catch (Exception e){
                e.printStackTrace();
                return result;
            }
            if(result.isFlag()){
                // 预约成功，可以为用户发送短信  预约成功发送的短信，ORDER_NOTICE
                try {
                    SMSUtils.sendMessage(telephone, (String) map.get("orderDate"));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            return result;
        }else{
            //        如果不成功，返回结果给页面
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

//    根据预约Id查询预约相关信息
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map map= orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
