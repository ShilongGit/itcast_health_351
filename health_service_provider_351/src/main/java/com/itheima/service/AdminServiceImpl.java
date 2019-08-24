package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConst;
import com.itheima.dao.AdminDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDao adminDao;

    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<SysUser> checkGroups = adminDao.findPageByCondition(queryPageBean.getQueryString());

        return new PageResult(checkGroups.getTotal(),checkGroups);

    }

    @Override
    public Result addUserWithcheckRoleId(SysUser user, Integer[] checkRoleIds) {
        try {

            adminDao.insertUser(user);


            if (checkRoleIds!= null && checkRoleIds.length > 0){
                Map<String,Object> params = new HashMap<>();
                params.put("userId",user.getId());
                params.put("checkRoleIds", checkRoleIds);
                adminDao.insertUserAndRoleGuanXi(params);
            }
            return new Result(true, "添加用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加用户失败");
        }
    }

    @Override
    public Map<String, Object> findUserById(Integer uid) {
        Map<String,Object> resultMap = new HashMap<>();

        SysUser user = adminDao.selectUserById(uid);

        List<Role> all = adminDao.findAllRole();

        List<Integer> roleIds = adminDao.selectCheckRoleIdsByUid(uid);


        resultMap.put("formData",user);
        resultMap.put("tableData",all);
        resultMap.put("checkRoleIds",roleIds);

        return resultMap;
    }

    @Override
    public void edit(Integer[] checkRoleIds, SysUser user) {

        adminDao.update(user);

        adminDao.deleteAssociation(user.getId());
        if (checkRoleIds!= null && checkRoleIds.length > 0){

            Map<String,Object> params = new HashMap<>();
            params.put("userId",user.getId());
            params.put("checkRoleIds", checkRoleIds);
            adminDao.insertUserAndRoleGuanXi(params);
        }

    }

    @Override
    @Transactional
    public void delById(Integer id) {

            adminDao.deleteAssociation(id);

            adminDao.delById(id);

    }

    @Override
    public List<Role> findAll() {
        return adminDao.findAll();
    }
}
