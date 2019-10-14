package com.golday.dao;

import com.github.pagehelper.Page;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.CheckItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CheckgroupDao {
    List<CheckItem> allCheckItem();

    int addCheckGroup(CheckGroup checkGroup);

    Page<CheckGroup> allCheckGroup(String queryString);

    int findGroupPackage(int id);

    void deleteCheckGroup(int id);

    void addCheckGroupCheckItem(Map<String, Integer> map);

    CheckGroup findCheckgroup(int id);

    Integer[] findCheckitemIds(int id);

    void updateCheckgroup(CheckGroup checkGroup);

    void deleteGroupItem(Integer id);
}
