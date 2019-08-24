package com.itheima.service;

import com.itheima.entity.QueryPageBean;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupService {
    PageResult findPageByCondition(QueryPageBean queryPageBean);

    Result addCheckGroupWithCheckItemId(CheckGroup checkGroup, Integer[] checkitemIds);

    Map<String,Object> findCheckGroupById(Integer gid);

    void edit(Integer[] checkitemIds, CheckGroup checkGroup);

    void delById(Integer id);

    List<CheckGroup> findAll();
}
