package com.golday.service;

import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.exception.MyException2;
import com.golday.exception.MyException3;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.CheckItem;

import java.util.List;

public interface CheckgroupService {
    List<CheckItem> allCheckItem() throws Exception;

    void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) throws MyException2;

    void groupAddCheck(List<Integer> value);

    PageResult<CheckGroup> allCheckGroup(QueryPageBean queryPageBean) throws Exception;

    void deleteCheckGroup(int id) throws MyException3;

    CheckGroup findCheckgroup(int id);

    Integer[] findCheckitemIds(int id);

    void updateCheckgroup(CheckGroup checkGroup, Integer[] checkitemIds);
}
