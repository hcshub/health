package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.golday.constant.MessageConstant;
import com.golday.dao.MemberDao;
import com.golday.dao.OrderDao;
import com.golday.dao.OrdersettingDao;
import com.golday.exception.MyException;
import com.golday.pojo.Member;
import com.golday.pojo.Order;
import com.golday.pojo.OrderSetting;
import com.golday.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrdersettingDao ordersettingDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    @Transactional
    public Order submitMessage(Map<String, String> map) throws MyException{
        String telephone = map.get("telephone");
        String orderDate = map.get("orderDate");
        String setmealId = map.get("setmealId");
        //校验用户预约的套餐是否还有空位
        OrderSetting orderSetting = ordersettingDao.findOrderSetting(orderDate);
        if (orderSetting == null) {
            throw new MyException("今天此套餐没有预约");
        }
        Integer number = orderSetting.getNumber();
        Integer reservations = orderSetting.getReservations();
        if (number == reservations) {
            //此套餐预约已满
            throw new MyException(MessageConstant.ORDER_FULL);
        }
        //套餐可预约,判断用户是否是会员(不是会员,肯定没有预约过)
        Member member = memberDao.findMemberByTelephone(telephone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = simpleDateFormat.parse(orderDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new MyException("日期解析异常");
        }
        if (member != null) {
            //是会员,判断用户是否预约过
            Order order = new Order(member.getId(), date,null, null, Integer.valueOf(setmealId));
            order = orderDao.checkOrder(order);
            if (order != null) {
                //用户已预约
                throw new MyException(MessageConstant.HAS_ORDERED);
            }
        }else {
            //不是会员,新增成会员
            member = new Member();
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdCard(map.get("idCard"));
            member.setPhoneNumber(map.get("telephone"));
            member.setRegTime(new Date());
            //这个添加需要,返回id
            memberDao.addMember(member);
        }
        //用户没有预约记录, 故对用户进行预约添加操作
        // 1. 生成一条预约信息 2. 当天套餐预约数 +1
        Order order = new Order(member.getId(), date, map.get("orderType"), map.get("orderStatus"), Integer.valueOf(setmealId));
        //这个添加需要返回id
        orderDao.addOrder(order);
        ordersettingDao.updateAutoNumber(orderDate);
        return order;
     /*   //校验客户是否已经预约
        Order order = orderDao.checkOrder(telephone,orderDate,setmealId);
        if (null != order) {
            //用户已预约
            throw new MyException(MessageConstant.HAS_ORDERED);
        }
        //用户预约记录,查看用户预约当天是否还有预约名额
       OrderSetting orderSetting = orderDao.showOrder(orderDate);
        if (orderSetting == null) {
            //当天未设置预约数
            throw new MyException(MessageConstant.ORDER_FULL);
        }
        if (orderSetting.getNumber() == orderSetting.getReservations()) {
            //预约已满
            throw new MyException(MessageConstant.ORDER_FULL);
        }
            //可预约,查看用户是否是会员, 是 获取id, 不是, 创建会员,并获取id
            Member member = MemberDao.checkMember(telephone);
            if (null == member) {
                member.setName(map.get("name"));
                member.setSex(map.get("sex"));
                member.setIdCard(map.get("idCard"));
                member.setPhoneNumber(map.get("telephone"));
                member.setRegTime(new Date());
                MemberDao.addMember(member);
            }
            Integer memberId = member.getId();
            //在订单表创建此用户订单
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(orderDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new MyException("日期转换失败");
        }
        order = new Order(memberId, parse, map.get("orderType"), map.get("sorderStatus"), Integer.valueOf(setmealId));
        //这个需要获取自增长的id
        orderDao.addOrder(order);
        //在预约表的预约记录添加 1
        ordersettingDao.updateAutoNumber(orderDate);
        return order;*/
    }

    @Override
    public Map<String, String> findMessage(int id) {
        return orderDao.findMessage(id);
    }
}
