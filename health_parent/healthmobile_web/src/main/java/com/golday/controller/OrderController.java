package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.constant.RedisMessageConstant;
import com.golday.entity.Result;
import com.golday.exception.MyException;
import com.golday.pojo.Order;
import com.golday.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submitMessage")
    public Result submitMessage(@RequestBody Map<String,String> map) throws MyException {
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        //String key = "Order_" + RedisMessageConstant.SENDTYPE_ORDER + telephone;
        String key = "order";
        Jedis jedis = jedisPool.getResource();
        String aaa = jedis.get("aaa");
        String bbb = jedis.get("bbb");
        String code = jedis.get(key);
        if (null == code || code.length() == 0) {
            //验证码为空
            return new Result(false,"验证码已过期,请重新获取");
        }
        if (!validateCode.equalsIgnoreCase(code)) {
            //验证码错误
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        //验证码正确
//        jedis.del(key);
        map.put("orderType","微信预约");
        map.put("orderStatus","未就诊");
        Order order = orderService.submitMessage(map);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @RequestMapping("/findMessage")
    public Result findMessage(int id){
        Map<String,String> map = orderService.findMessage(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
    }
}
