package com.golday.controller;

import com.aliyuncs.exceptions.ClientException;
import com.golday.constant.MessageConstant;
import com.golday.constant.RedisMessageConstant;
import com.golday.entity.Result;
import com.golday.exception.MyException;
import com.golday.utils.SMSUtils;
import com.golday.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/orderCode")
public class OrderCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/checkCode")
    public Result checkCode(String telephone){
        Jedis jedis = jedisPool.getResource();
        String key = "Order_" + RedisMessageConstant.SENDTYPE_ORDER + telephone;
        String code = jedis.get(key);
        if (null != code) {
            return new Result(false,"验证码已发送,请注意查收");
        }
        Integer redisCode = ValidateCodeUtils.generateValidateCode(6);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone+"",redisCode+"");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        jedis.setex(key,5*60,redisCode+"");
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
