package com.golday.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, String> map = getMap(username);
        Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        set.add(grantedAuthority);
        User user = new User(username,map.get("password"),set);
        return user;
    }

    public static Map<String, String> getMap(String username){
        Map<String,String> map = new HashMap<>();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String admin = bcrypt.encode("admin");
        map.put("username",username);
        map.put("password",admin);
        return map;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String admin = bcrypt.encode("admin");
        System.out.println(admin);
    }
}
