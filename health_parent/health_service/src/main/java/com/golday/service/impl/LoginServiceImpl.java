package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.golday.dao.LoginDao;
import com.golday.dao.MemberDao;
import com.golday.pojo.Member;
import com.golday.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Service(interfaceClass = LoginService.class)
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public void fastLogin(String telephone) {
        //先判断是否有此用户,有则登录,无则,添加用户,再转登录
        Member member = loginDao.findMemberByTelephone(telephone);
        if (member == null) {
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberDao.addMember(member);
        }
    }
}
