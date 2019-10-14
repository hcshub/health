package service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pojo.User;

import java.util.ArrayList;
import java.util.List;

public class myUserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = findByUsername(username);
        if (null!=byUsername) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            grantedAuthorities.add(simpleGrantedAuthority);
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username,  byUsername.getPassword(), grantedAuthorities);
            return user;
        }
        return null;
    }

    private User findByUsername(String username){
        User user = null;
        if("admin".equals(username)) {
            user = new User();
            user.setUsername("admin");
            user.setPassword("$2a$10$D5HxpgvtYqnWLKkx3PNOLebU/XFyVvIFoBgIyhSdoLt6bRHg3hz7q");
            user.setId(9527);
        }
        return user;
    }
}
