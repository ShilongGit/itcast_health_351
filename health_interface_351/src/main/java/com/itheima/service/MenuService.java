package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    void add(Menu menu);

    Map<String,Object> findParentMenu();

    PageResult findPage(QueryPageBean queryPageBean);

    void delById(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    List<Menu> findAll();
}
