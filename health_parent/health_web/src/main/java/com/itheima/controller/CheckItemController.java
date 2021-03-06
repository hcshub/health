package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;
    @Autowired
    JedisPool jedisPool;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
        //添加前清理缓存
        Jedis jedis = jedisPool.getResource();
        jedis.del(RedisConstant.SETMEAL_NAME_DETAIL);

        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete")
    public Result delete(int id){
        checkItemService.delete(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem= checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        //修改前前清理缓存
        Jedis jedis = jedisPool.getResource();
        jedis.del(RedisConstant.SETMEAL_NAME_DETAIL);

        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
