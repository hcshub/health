package com.golday.dao;

import com.golday.pojo.Member;

public interface LoginDao {
    Member findMemberByTelephone(String telephone);
}
