package com.golday.dao;

import com.golday.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface OrderDao {
    Order checkOrder(Order order);

    void addOrder(Order order);

    Map<String,String> findMessage(int id);

    int findByTodayVisitsNumber(@Param("today")String today,@Param("orderstatusYes") String orderstatusYes);

    int findByThisWeekVisitsNumber(@Param("beginDate") String beginDate,@Param("endDate") String endDate,@Param("orderstatusYes") String orderstatusYes);

    int findByThisMonthVisitsNumber(@Param("monthStartDay") String monthStartDay,@Param("monthLastDay") String monthLastDay,@Param("orderstatusYes") String orderstatusYes);
}
