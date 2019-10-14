package com.golday.dao;

import com.golday.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberDao {
    Member findMemberByTelephone(String telephone);

    void addMember(Member member);

    int findByTodayMmber(String today);

    int findByThisWeekNewMember(@Param("beginDate") String beginDate,@Param("endDate") String endDate);

    int findByTotalMember();

    int findByThisMonthNewMember(@Param("monthStartDay") String monthStartDay,@Param("monthLastDay") String monthLastDay);
}
