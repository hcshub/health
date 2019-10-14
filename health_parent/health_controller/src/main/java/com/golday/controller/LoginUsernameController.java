package com.golday.controller;

import com.golday.constant.MessageConstant;
import com.golday.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginUsername")
public class LoginUsernameController {

    @RequestMapping("/findUsername")
    public Result findUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,username);
    }
}
