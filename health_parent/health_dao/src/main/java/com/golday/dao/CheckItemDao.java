package com.golday.dao;

import com.github.pagehelper.Page;
import com.golday.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> findCodeName(String queryString);

    int findCheck(int id);

    void delete(int id);

    CheckItem showOne(int id);

    void updateCheckItem(CheckItem checkItem);
}
