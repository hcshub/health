package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.constant.RedisConstant;
import com.golday.constant.RedisMessageConstant;
import com.golday.entity.Result;
import com.golday.pojo.Member;
import com.golday.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private LoginService loginService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/fastLogin")
    public Result fastLogin(@RequestBody Map<String,String> map, HttpServletResponse response){
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        Jedis jedis = jedisPool.getResource();
        String key = "Login_" + RedisMessageConstant.SENDTYPE_LOGIN + telephone;
//        String key = "login_code";
        String code = jedis.get(key);
        //校验验证码是否正确
        if (!validateCode.equalsIgnoreCase(code)) {
            jedis.del(key);
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        loginService.fastLogin(telephone);
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setMaxAge(60*60*24*30);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
