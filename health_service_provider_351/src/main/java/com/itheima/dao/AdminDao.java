package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;

import java.util.List;
import java.util.Map;

public interface AdminDao {
    Page<SysUser> findPageByCondition(String queryString);

    void insertUser(SysUser user);

    void insertUserAndRoleGuanXi(Map<String, Object> params);

    SysUser selectUserById(Integer uid);

    List<Role> findAllRole();

    List<Integer> selectCheckRoleIdsByUid(Integer uid);

    void update(SysUser user);

    void deleteAssociation(Integer id);

    void delById(Integer id);

    List<Role> findAll();
}
