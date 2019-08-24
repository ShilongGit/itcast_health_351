package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;

import java.util.List;
import java.util.Map;

public interface AdminService {
    PageResult findPageByCondition(QueryPageBean queryPageBean);

    Result addUserWithcheckRoleId(SysUser user, Integer[] checkRoleIds);

    Map<String,Object> findUserById(Integer uid);

    void edit(Integer[] checkRoleIds, SysUser user);

    void delById(Integer id);

    List<Role> findAll();
}
