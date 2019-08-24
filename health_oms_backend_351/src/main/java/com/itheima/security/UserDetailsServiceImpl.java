package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       SysUser dbuser = userService.findUserByName(username);

       if (dbuser == null){
           return null;
       }

        List<GrantedAuthority> authorityList = new ArrayList<>();

        for (Role role : dbuser.getRoles()) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getKeyword());
            authorityList.add(simpleGrantedAuthority);
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority simpleGrantedAuthority1 = new SimpleGrantedAuthority(permission.getKeyword());
                authorityList.add(simpleGrantedAuthority1);
            }
        }


        return new org.springframework.security.core.userdetails.User(dbuser.getUsername(),dbuser.getPassword(),authorityList);
    }
}
