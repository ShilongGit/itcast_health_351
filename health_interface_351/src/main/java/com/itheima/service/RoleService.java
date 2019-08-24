package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;

import java.util.Map;

public interface RoleService {
    PageResult findPageByCondition(QueryPageBean queryPageBean);

    Result addRoleWithConditionIds(Role role, Integer[] checkMenuIds, Integer[] checkPermissionIds);

    Map<String,Object> findUserById(Integer id);

    void edit(Role role, Integer[] checkMenuIds, Integer[] checkPermissionIds);

    void delById(Integer id);

    Map<String,Object> findAll();
}
