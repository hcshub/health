package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.entity.Result;
import com.golday.pojo.CheckItem;
import com.golday.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) throws Exception {
        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/pageSearch")
    public Result pageSearch(@RequestBody QueryPageBean queryPageBean) throws Exception {
        PageResult pageResult = checkItemService.pageSearch(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    @RequestMapping("/delete")
    public Result delete(int id){
        checkItemService.delete(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/showOne")
    public Result showOne(int id) throws Exception {
        CheckItem checkItem = checkItemService.showOne(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @RequestMapping("/updateCheckItem")
    public Result updateCheckItem(@RequestBody CheckItem checkItem) throws Exception {
        checkItemService.updateCheckItem(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
