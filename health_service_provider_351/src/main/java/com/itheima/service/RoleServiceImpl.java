package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    MenuDao menuDao;

    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Role> checkGroups = roleDao.findPageByCondition(queryPageBean.getQueryString());

        return new PageResult(checkGroups.getTotal(),checkGroups);
    }

    @Override
    public Result addRoleWithConditionIds(Role role, Integer[] checkMenuIds, Integer[] checkPermissionIds) {
        try {

            roleDao.insertRole(role);


            if (checkMenuIds!= null && checkMenuIds.length > 0){
                Map<String,Object> params = new HashMap<>();
                params.put("roleId",role.getId());

                List<Integer> menuIdList = new ArrayList<>();

                for (Integer checkMenuId : checkMenuIds) {
                    List<Integer> childIds = roleDao.findChildMenuIdsByPid(checkMenuId);
                    menuIdList.addAll(childIds);
                    menuIdList.add(checkMenuId);
                }

                params.put("checkMenuIds", menuIdList);
                roleDao.insertRoleAndMenuGuanXi(params);
            }

            if (checkPermissionIds!= null && checkPermissionIds.length > 0){
                Map<String,Object> params = new HashMap<>();
                params.put("roleId",role.getId());
                params.put("checkPermissionIds", checkPermissionIds);
                roleDao.insertRoleAndPermissionGuanXi(params);
            }
            return new Result(true, "添加角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加角色失败");
        }
    }

    @Override
    public Map<String, Object> findUserById(Integer id) {
        Map<String,Object> resultMap = new HashMap<>();

        Role role = roleDao.findRoleById(id);

        List<Permission> permissionTableData = permissionDao.findAll();

        List<Menu> menuTableData = menuDao.findParentMenu();

        List<Integer> checkMenuIds = roleDao.findCheckParentMenuIdsByRoleId(id);

        List<Integer> checkPermissionIds = roleDao.findCheckPermissionIdsByRoleId(id);

        resultMap.put("formData",role);
        resultMap.put("permissionTableData",permissionTableData);
        resultMap.put("menuTableData",menuTableData);
        resultMap.put("checkMenuIds",checkMenuIds);
        resultMap.put("checkPermissionIds",checkPermissionIds);

        return resultMap;
    }

    @Override
    public void edit(Role role, Integer[] checkMenuIds, Integer[] checkPermissionIds) {
        roleDao.update(role);

        roleDao.deleteRoleAndMenuGuanXi(role.getId());
        roleDao.deleteRoleAndPermissionGuanXi(role.getId());

        if (checkMenuIds!= null && checkMenuIds.length > 0){

            Map<String,Object> params = new HashMap<>();
            params.put("roleId",role.getId());

            List<Integer> menuIdList = new ArrayList<>();

            for (Integer checkMenuId : checkMenuIds) {
                List<Integer> childIds = roleDao.findChildMenuIdsByPid(checkMenuId);
                menuIdList.addAll(childIds);
                menuIdList.add(checkMenuId);
            }



            params.put("checkMenuIds", checkMenuIds);
            roleDao.insertRoleAndMenuGuanXi(params);
        }

        if (checkPermissionIds!= null && checkPermissionIds.length > 0){
            Map<String,Object> params = new HashMap<>();
            params.put("roleId",role.getId());
            params.put("checkPermissionIds", checkPermissionIds);
            roleDao.insertRoleAndPermissionGuanXi(params);
        }
    }

    @Override
    @Transactional
    public void delById(Integer id) {

        roleDao.deleteRoleAndMenuGuanXi(id);
        roleDao.deleteRoleAndPermissionGuanXi(id);
        roleDao.deleteRoleById(id);

    }

    @Override
    public Map<String, Object> findAll() {
        Map<String,Object> resultMap = new HashMap<>();

        List<Permission> permissionTableData = permissionDao.findAll();

        List<Menu> menuTableData = menuDao.findParentMenu();

        resultMap.put("permissionTableData",permissionTableData);
        resultMap.put("menuTableData",menuTableData);

        return resultMap;
    }
}
