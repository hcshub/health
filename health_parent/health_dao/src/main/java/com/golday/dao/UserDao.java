package com.golday.dao;

import com.golday.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User findByUsername(String username);

    int findByNumber(String time);

    List<Map<String,Object>> findByCheckgroupReport();
}
