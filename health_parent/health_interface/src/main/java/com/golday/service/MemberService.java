package com.golday.service;

import com.golday.pojo.User;

import java.util.List;
import java.util.Map;

public interface MemberService {
    User findByUsername(String username);

    Map<String,List<Object>> findByMemberReport();

    Map<String, Object> findByPackageReport();

    Map<String,Object> findByBusinessReport();
}
