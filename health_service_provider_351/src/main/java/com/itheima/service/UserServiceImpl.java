package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    MenuDao menuDao;

    @Autowired
    PermissionDao permissionDao;

    @Override
    public SysUser findUserByName(String username) {

        SysUser user = userDao.findUserByName(username);

        Set<Role> roles = roleDao.findRoleByUserId(user.getId());

        for (Role role : roles) {
            Set<Permission> permissions = permissionDao.findPermissionByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        user.setRoles(roles);

        return user;
    }

    @Override
    public Result findMenuByName(String username) {
        //需要组合好子菜单的父菜单集合
        SysUser userByName = userDao.findUserByName(username);

        List<Menu> parentMenuList = new ArrayList<>();
        List<Menu> menuList = menuDao.findMenuListByUserId(userByName.getId());

        if (menuList != null){
            for (Menu menu : menuList) {
                if (menu.getParentMenuId() == null){
                    parentMenuList.add(menu);
                }
            }

            for (Menu menu : parentMenuList) {
                //List<Menu> childMenuList = menuDao.findChidMenuByPid(menu.getId());
                List<Menu> childMenuList = new ArrayList<>();
                for (Menu child : menuList) {
                    if (child.getParentMenuId() == menu.getId()){
                        childMenuList.add(child);
                    }
                }
                menu.setChildren(childMenuList);
            }
        }

        return new Result(true,"",parentMenuList);

    }
}
