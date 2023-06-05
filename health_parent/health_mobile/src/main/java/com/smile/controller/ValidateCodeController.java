package com.smile.controller;

import com.smile.constant.MessageConstant;
import com.smile.constant.RedisMessageConstant;
import com.smile.entity.Result;
import com.smile.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.JedisPool;

import java.util.Random;

//验证码操作
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

//    用户在线体检预约发送验证码
    @RequestMapping("/sendForOrder")
    public Result sendForOrder(String telephone){
//        随机生成4位数字验证码
//        String phone= user.getPhone();
        String[] letters = new String[]{
                "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
                "A", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(letters[(int) Math.floor(Math.random() * letters.length)]);
        }
        //获取6位随机验证码（中文），根据项目需要选择中英文
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("验证码为："+code);
//        给用户发送验证码
        if(StringUtils.isNotEmpty(telephone)) {
            SMSUtils.sendMessage(telephone, code);
//        将验证码保存到redis(5分钟)，单纯用手机号作为key，不能解决 同一个手机号快速登录和预约
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
        return null;
    }

    //    用户手机快速登录发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
//        随机生成6位数字验证码
//        String phone= user.getPhone();
        String[] letters = new String[]{
                "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
                "A", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(letters[(int) Math.floor(Math.random() * letters.length)]);
        }
        //获取6位随机验证码（中文），根据项目需要选择中英文
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("验证码为："+code);
//        给用户发送验证码
        if(StringUtils.isNotEmpty(telephone)) {
            SMSUtils.sendMessage(telephone, code);
//        将验证码保存到redis(5分钟)，单纯用手机号作为key，不能解决 同一个手机号快速登录和预约
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,300,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
        return null;
    }

}
