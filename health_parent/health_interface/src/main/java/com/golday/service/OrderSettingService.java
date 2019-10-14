package com.golday.service;

import com.golday.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void importFile(List<OrderSetting> list);

    List<Map<String, Integer>> getOrder(String date);

    void updateNumber(OrderSetting orderSetting);
}
