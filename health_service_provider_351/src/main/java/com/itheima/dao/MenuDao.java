package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> findMenuListByUserId(Integer id);

    Page<Menu> findByCondition(String queryString);

    List<Menu> findParentMenu();

    void delById(Integer id);

    void delMenuWithRoleByMenuId(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    List<Menu> findAll();

    int findMaxPath4ParentMenu();

    Menu findParentMenuByPath(String basePath);

    long findChildMenuCountByPid(Integer id);

    void insertMenu(Menu menu);

    void insertMenuWithRoleByMid(Integer id);
}
