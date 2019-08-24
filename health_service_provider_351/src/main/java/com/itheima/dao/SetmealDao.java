package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    Page<Setmeal> findPageByCondition(String queryString);

    void insertSetmeal(Setmeal setmeal);

    void insertSetmealIdWithCheckgroupIds(Map<String, Object> paramMap);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
