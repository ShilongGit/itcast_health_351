package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    @Transactional
    public void add(Permission permission) {
        //主键返回策略
        permissionDao.insertPermission(permission);
        //付给admin权限-关联表
        permissionDao.giveAdminPermission(permission.getId());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 条件查询
        Page<Permission> page =  permissionDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    @Override
    @Transactional
    public void delById(Integer id) {

        //删除关联表数据
        permissionDao.delPermissionWithRole(id);

        permissionDao.delById(id);

    }

    @Override
    public Permission findById(Integer id) {
        return permissionDao.selectPermissionById(id);
    }

    @Override
    public void edit(Permission permission) {
            permissionDao.edit(permission);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
