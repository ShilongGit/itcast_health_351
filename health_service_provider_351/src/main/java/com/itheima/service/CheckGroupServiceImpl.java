package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConst;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<CheckGroup> checkGroups = checkGroupDao.findPageByCondition(queryPageBean.getQueryString());

        return new PageResult(checkGroups.getTotal(),checkGroups);
    }

    @Override
    public Result addCheckGroupWithCheckItemId(CheckGroup checkGroup, Integer[] checkitemIds) {
        try {

            checkGroupDao.insertCheckGroup(checkGroup);

            System.out.println();
            Map<String,Object> params = new HashMap<>();
            params.put("checkGroupId",checkGroup.getId());
            params.put("checkitemIds", Arrays.asList(checkitemIds));
            checkGroupDao.insertGroupAndItemsGuanXi(params);
            return new Result(true,MessageConst.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.ADD_CHECKGROUP_FAIL);
        }
    }

    @Override
    public Map<String, Object> findCheckGroupById(Integer gid) {

        Map<String,Object> resultMap = new HashMap<>();
//        formData: {},//表单数据
        CheckGroup checkGroup = checkGroupDao.selectCheckGroupById(gid);
//        tableData:[],//新增和编辑表单中对应的检查项列表数据

        List<CheckItem> all = checkItemDao.findAll();

        List<CheckItem> checkItemList = checkGroupDao.selectCheckItemByGid(gid);
//        checkitemIds:[],//
        List<Integer> cid = new ArrayList<>();
        for (CheckItem checkItem : checkItemList) {
            cid.add(checkItem.getId());
        }

        resultMap.put("formData",checkGroup);
        resultMap.put("tableData",all);
        resultMap.put("checkitemIds",cid);

        return resultMap;
    }

    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        //1.修改检查组
        checkGroupDao.update(checkGroup);
        //2.1 先删除该检查组原来的关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //2.2 添加新的关系

        Map<String,Object> params = new HashMap<>();
        params.put("checkGroupId",checkGroup.getId());
        params.put("checkitemIds", Arrays.asList(checkitemIds));
        checkGroupDao.insertGroupAndItemsGuanXi(params);
//        if(checkGroup.getId() != null && checkitemIds != null && checkitemIds.length > 0){
//            for (Integer checkitemId : checkitemIds) {
//                checkGroupDao.set(checkGroup.getId(),checkitemId);
//            }
//        }
    }

    @Override
    public void delById(Integer id) {
        Long count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if(count == 0){
            checkGroupDao.deleteAssociation(id);
            checkGroupDao.delById(id);
        }else{
            throw new RuntimeException("检查组被套餐关联,不能删除！！！");
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
