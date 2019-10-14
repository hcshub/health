package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.golday.dao.OrdersettingDao;
import com.golday.exception.OrderSettingException;
import com.golday.pojo.OrderSetting;
import com.golday.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrdersettingDao ordersettingDao;

    @Override
    @Transactional
    public void importFile(List<OrderSetting> list) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (null != list && list.size() > 0) {
            List<String> dates = new ArrayList<>();
            for (OrderSetting orderSetting : list) {
                String format = simpleDateFormat.format(orderSetting.getOrderDate());
                dates.add(format);
            }
            //查询此次提交的文件中,有哪些日期在数据库中已有数据,并返回已有数据的日期集合
            List<String> dateString = ordersettingDao.findDate(dates);
            //遍历文件日期,已有的执行更新操作,没有的执行添加操作
            for (OrderSetting orderSetting : list) {
                Date orderDate = orderSetting.getOrderDate();
                String date = simpleDateFormat.format(orderDate);
                Integer number = orderSetting.getNumber();
                String format = simpleDateFormat.format(orderDate);
                if (dateString.contains(format)) {
                 //此日期在数据库中已存在;更新
                    ordersettingDao.updateOrderSetting(date,number);
                }else {
                    //不存在, 新增
                    ordersettingDao.addOrderSettring(date,number);
                }
            }
        }
    }

    @Override
    public List<Map<String, Integer>> getOrder(String date) {
        String beginDate = date + "-1";
        String endDate = date + "-31";
        List<OrderSetting> list = ordersettingDao.getOrder(beginDate,endDate);
        List<Map<String, Integer>> list2 = new ArrayList<Map<String, Integer>>();
        SimpleDateFormat day = new SimpleDateFormat("d");
        for (OrderSetting orderSetting : list) {
            Map<String,Integer> map = new HashMap<>();
            Integer ri = Integer.valueOf(day.format(orderSetting.getOrderDate()));
            Integer number = orderSetting.getNumber();
            Integer reservations = orderSetting.getReservations();
            map.put("date",ri);
            map.put("number",number);
            map.put("reservations",reservations);
            list2.add(map);
        }
        return list2;
    }

    @Override
    public void updateNumber(OrderSetting orderSetting) {
        // 1. 查询修改日期是否存在
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(orderSetting.getOrderDate());
        OrderSetting orderSetting1 = ordersettingDao.findOrderSetting(date);
        Integer number = orderSetting.getNumber();
        if (null != orderSetting1) {
        // 3. 存在, 判断修改预约数是否大于等于,已预约数,大于,正常修改,反之,报错
            if (number < orderSetting1.getReservations()) {
                throw new OrderSettingException("设置预约数小于当天已经预约的人数");
            }else {
                ordersettingDao.updateOrderSetting(date,number);
            }
        }else {
        // 2. 不存在, 执行添加
            ordersettingDao.addOrderSettring(date,number);
        }
    }
}
