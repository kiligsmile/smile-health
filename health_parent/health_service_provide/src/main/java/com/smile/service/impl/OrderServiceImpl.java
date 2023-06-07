package com.smile.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.smile.constant.MessageConstant;
import com.smile.dao.MemberDao;
import com.smile.dao.OrderDao;
import com.smile.dao.OrderSettingDao;
import com.smile.entity.Result;
import com.smile.pojo.Member;
import com.smile.pojo.Order;
import com.smile.pojo.OrderSetting;
import com.smile.service.OrderService;
import com.smile.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;
    @Override
    public Result order(Map map) throws Exception{

//        1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting= orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if(orderSetting==null){
//            指定日期没有进行预约设置，无法完成体检预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
//        2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number=orderSetting.getNumber(); // 可预约人数
        int reservations=orderSetting.getReservations();  // 已预约人数
        if(reservations>=number){
//            已约满，无法预约
            return new Result(false,MessageConstant.ORDER_FULL);
        }
//        3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
//        4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(member!=null){
//            判断是否重复预约
            Integer memberId = member.getId();   // 会员ID
            Date order_Date= DateUtils.parseString2Date(orderDate); // 预约日期
            String setmealId = (String) map.get("setmealId");  // 套餐ID
            Order order = new Order(memberId, order_Date, Integer.parseInt(setmealId));
//            根据条件进行查询
            List<Order> list = orderDao.findByCondition(order);
            if(list!=null&&list.size()>0){
//                说明用户在重复预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else{
            //当前用户不是会员，需要添加到会员表
            member=new Member();
            member.setPhoneNumber(telephone);
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);  // 自动完成会员注册
        }

//        5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());   // 设置会员Id
        order.setOrderDate(DateUtils.parseString2Date(orderDate));  // 预约日期
        order.setOrderType((String) map.get("orderType")); // 预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO); // 到诊状态
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId"))); // 套餐ID
        orderDao.add(order);
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editNumberByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

//    根据预约ID查询预约相关信息（体检人姓名、预约日期、套餐名称、预约类型）
    public Map findById(Integer id) {
        Map map = orderDao.findById4Detail(id);
        if(map!=null){
//            处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            try {
                map.put("orderDate",DateUtils.parseDate2String(orderDate));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
