package com.golday.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.golday.service.ClearService;
import com.golday.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

@Controller
public class ClearPictrue {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private ClearService clearService;

    @RequestMapping("/clear")
    public String clearImg(){
        System.out.println("开始执行清除任务...");
        Jedis jedis = jedisPool.getResource();
        List<String> list = clearService.findImgs();
        if (list != null && list.size() > 0) {
            for (String img : list) {
                jedis.sadd("mysql",img);
            }
        }

        Set<String> imgSet = jedis.sdiff("qiniuyun", "mysql");
        if (imgSet != null && imgSet.size() > 0) {
            for (String img : imgSet) {
                QiniuUtils.deleteFileFromQiniu(img);
                jedis.srem("qiniuyun",img);
            }
        }
        return "redirect:/success.html";
    }
}
