package com.golday.service;

import com.golday.pojo.Order;

import java.util.Map;

public interface OrderService {
    Order submitMessage(Map<String, String> map);

    Map<String,String> findMessage(int id);
}
