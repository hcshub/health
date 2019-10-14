package com.golday.dao;

import com.golday.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdersettingDao {
    List<String> findDate(List<String> dates);

    void updateOrderSetting(@Param("date") String date,@Param("number") Integer number);

    void addOrderSettring(@Param("date") String date,@Param("number") Integer number);

    List<OrderSetting> getOrder(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    OrderSetting findOrderSetting(String date);

    void updateAutoNumber(String orderDate);

    int findByTodayOrderNumber(String today);

    int findThisWeekOrderNumber(@Param("beginDate") String beginDate,@Param("endDate") String endDate);

    int findByThisMonthOrderNumber(@Param("monthStartDay") String monthStartDay,@Param("monthLastDay") String monthLastDay);
}
