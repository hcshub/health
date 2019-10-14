package com.golday.service;

import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.exception.MyException;
import com.golday.pojo.CheckItem;

public interface CheckItemService {
    void add(CheckItem checkItem) throws Exception;

    PageResult pageSearch(QueryPageBean queryPageBean) throws Exception;

    void delete(int id) throws MyException;

    CheckItem showOne(int id) throws Exception;

    void updateCheckItem(CheckItem checkItem) throws Exception;
}
