package com.golday.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.pojo.Permission;
import com.golday.pojo.Role;
import com.golday.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = memberService.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles != null) {
            GrantedAuthority grantedAuthority = null;
            for (Role role : roles) {
                //添加角色
                String roleKeyword = role.getKeyword();
                grantedAuthority = new SimpleGrantedAuthority(roleKeyword);
                list.add(grantedAuthority);
                //添加权限
                Set<Permission> permissions = role.getPermissions();
                if (permissions != null) {
                    for (Permission permission : permissions) {
                        String keyword = permission.getKeyword();
                        grantedAuthority = new SimpleGrantedAuthority(keyword);
                        list.add(grantedAuthority);
                    }
                }
            }
        }
        org.springframework.security.core.userdetails.User userDetail =
                new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return userDetail;
    }
}
