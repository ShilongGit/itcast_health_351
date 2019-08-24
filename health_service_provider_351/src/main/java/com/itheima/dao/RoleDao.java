package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {
    Set<Role> findRoleByUserId(Integer id);

    Page<Role> findPageByCondition(String queryString);



    void insertRole(Role role);

    void insertRoleAndMenuGuanXi(Map<String, Object> params);

    void insertRoleAndPermissionGuanXi(Map<String, Object> params);

    Role findRoleById(Integer id);

    List<Integer> findCheckMenuIdsByRoleId(Integer id);

    List<Integer> findCheckPermissionIdsByRoleId(Integer id);

    void update(Role role);

    void deleteRoleAndMenuGuanXi(Integer id);

    void deleteRoleAndPermissionGuanXi(Integer id);

    void deleteRoleById(Integer id);

    List<Integer> findCheckParentMenuIdsByRoleId(Integer id);

    List<Integer> findChildMenuIdsByPid(Integer checkMenuId);
}
