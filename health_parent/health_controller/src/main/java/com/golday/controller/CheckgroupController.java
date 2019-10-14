package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.entity.Result;
import com.golday.exception.MyException3;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.CheckItem;
import com.golday.service.CheckgroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class CheckgroupController {
    @Reference
    private CheckgroupService checkgroupService;

    @RequestMapping("/allCheckItem")
    public Result allCheckItem() throws Exception {
        List<CheckItem> list = checkgroupService.allCheckItem();
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,list);
    }

    @RequestMapping("/addCheckgroup")
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    public Result addCheckgroup(@RequestBody CheckGroup checkGroup, @RequestParam Integer[] checkitemIds){
        checkgroupService.addCheckGroup(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/groupAddCheck")
    public Result groupAddCheck(@RequestBody List<Integer> value){
        checkgroupService.groupAddCheck(value);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/allCheckGroup")
    public Result allCheckGroup(@RequestBody QueryPageBean queryPageBean) throws Exception{
        PageResult<CheckGroup> pageResult = checkgroupService.allCheckGroup(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @RequestMapping("/deleteCheckGroup")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HEALTH_MANAGER')")
    public Result deleteCheckGroup(int id) throws MyException3 {
        checkgroupService.deleteCheckGroup(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findCheckgroup")
    public Result findCheckgroup(int id){
        CheckGroup checkGroup = checkgroupService.findCheckgroup(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    @RequestMapping("/findCheckitemIds")
    public Result findCheckitemIds(int id){
        Integer[] checkitemIds = checkgroupService.findCheckitemIds(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitemIds);
    }

    @RequestMapping("/updateCheckgroup")
    public Result updateCheckgroup(@RequestBody CheckGroup checkGroup,@RequestParam Integer[] checkitemIds){
        checkgroupService.updateCheckgroup(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
