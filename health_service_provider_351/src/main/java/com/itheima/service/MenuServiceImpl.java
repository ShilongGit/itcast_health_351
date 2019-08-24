package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Override
    @Transactional
    public void add(Menu menu) {

        if(menu.getLevel() == 1){
            int path = menuDao.findMaxPath4ParentMenu();
            menu.setIcon("el-icon-s-promotion");
            if (path == 0){
                path = 2;
                menu.setPath(String.valueOf(path));
            }else {
                menu.setPath(String.valueOf(path+1));
                menu.setPriority(path);
            }
        }else {
            String basePath = menu.getPath();
            Menu parentMenu = menuDao.findParentMenuByPath(basePath);
            int count = (int) menuDao.findChildMenuCountByPid(parentMenu.getId());
            if (count == 0) {
                count =1;
                menu.setPath("/"+basePath+"-"+(count));
                menu.setPriority(count);
            }else {
                menu.setPath("/"+basePath+"-"+(count+1));
                menu.setPriority(count+1);
            }
            menu.setParentMenuId(parentMenu.getId());
        }

        menuDao.insertMenu(menu);

        //主键返回-维护菜单与管理员的关联表
        menuDao.insertMenuWithRoleByMid(menu.getId());

    }

    @Override
    public Map<String, Object> findParentMenu() {

        Map<String, Object> resultMap = new HashMap<>();

        List<Menu> parentMenuList = menuDao.findParentMenu();

        resultMap.put("parentMenuList",parentMenuList);

        return resultMap;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 条件查询
        Page<Menu> page =  menuDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    @Override
    @Transactional
    public void delById(Integer id) {

        int count = (int) menuDao.findChildMenuCountByPid(id);
        if (count > 0){
            throw new RuntimeException("父菜单不为空");
        }
        //维护角色菜单关系
        menuDao.delMenuWithRoleByMenuId(id);
        //删除菜单
        menuDao.delById(id);

    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }
}
