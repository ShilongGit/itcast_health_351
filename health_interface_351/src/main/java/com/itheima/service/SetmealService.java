package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;

public interface SetmealService {
    PageResult findPageByCondition(QueryPageBean queryPageBean);

    Result addsetmealWithCheckGroup(Setmeal setmeal, Integer[] checkgroupIds);

    Result findAll();

    Result findById(Integer id);
}
