package com.smile.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.smile.dao.OrderSettingDao;
import com.smile.pojo.OrderSetting;
import com.smile.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//预约设置服务
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{
//    批量导入预约设置数据
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> list) {
        //检查此数据（日期）是否存在
        if(list!=null&&list.size()>0){
            for (OrderSetting orderSetting : list) {
//                判断当前日期是否已经进行了预约设置
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(countByOrderDate>0){
                    // 已经进行了预约设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
//                    没有进行预约设置，执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

//    根据月份查询对应的预约设置数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) {  // date格式为：yyyy-MM
        String begin =date+"-1";
        String end =date+"-31";  // 可以不用考虑当前月份是否有31天，并不影响查询结果
        Map<String,String> map=new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
//        调用dao，根据日期范围查询预约设置数据
        List<OrderSetting> list= orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result=new ArrayList<>();
        if(list!=null&&list.size()>0){
            for (OrderSetting orderSetting : list) {
                Map<String,Object> m=new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate()); // 获取日期数字（几号）
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    //    根据日期设置对应的预约设置数据
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //        根据日期查询是否已经进行了预约设置
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if(count>0){
//            当前日期已经进行了预约设置，需要执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
//            当前日期没有预约设置，需要执行插入操作
            orderSettingDao.add(orderSetting);
        }
    }
}
