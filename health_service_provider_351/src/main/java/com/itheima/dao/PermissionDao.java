package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    Set<Permission> findPermissionByRoleId(Integer id);

    void insertPermission(Permission permission);

    Page<Permission> findByCondition(String queryString);

    long findPermissionCountById(Integer id);

    void delById(Integer id);

    Permission selectPermissionById(Integer id);

    void edit(Permission permission);

    List<Permission> findAll();

    void giveAdminPermission(Integer id);

    void delPermissionWithRole(Integer id);
}
