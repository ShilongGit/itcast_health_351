package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {

    Page<CheckGroup> findPageByCondition(String queryString);

    void insertGroupAndItemsGuanXi(Map<String, Object> params);

    void insertCheckGroup(CheckGroup checkGroup);

    CheckGroup selectCheckGroupById(Integer gid);

    List<CheckItem> selectCheckItemByGid(Integer gid);

    void update(CheckGroup checkGroup);

    void set(Integer id, Integer checkitemId);

    Long findSetmealCountByCheckGroupId(Integer id);

    void deleteAssociation(Integer id);

    void delById(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupListBySetmealId(Integer id);
}
