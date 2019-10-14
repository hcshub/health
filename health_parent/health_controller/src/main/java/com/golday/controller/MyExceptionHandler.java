package com.golday.controller;

import com.golday.constant.MessageConstant;
import com.golday.entity.Result;
import com.golday.exception.MyException;
import com.golday.exception.MyException2;
import com.golday.exception.MyException3;
import com.golday.exception.OrderSettingException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(MyException.class)
    public Result myException(MyException message){
        message.printStackTrace();
        return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception unknown){
        unknown.printStackTrace();
        return new Result(false,"发生异常了...");
    }

    @ExceptionHandler(MyException2.class)
    public Result checkgroupException(MyException2 myException2){
        myException2.printStackTrace();
        return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    @ExceptionHandler(MyException3.class)
    public Result checkgroupPackage(MyException3 myException3){
        myException3.printStackTrace();
        return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
    }

    @ExceptionHandler(OrderSettingException.class)
    public Result orderSettingException(OrderSettingException orderException){
        orderException.printStackTrace();
        return new Result(false,"设置的预约数小于当天已经预约的人数");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result userAccess(AccessDeniedException accessDeniedException){
        accessDeniedException.printStackTrace();
        return new Result(false,"权限不足");
    }
}
