package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SystemLogDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MySystemLogServiceImpl implements MySystemLogService {

    @Autowired
    SystemLogDao systemLogDao;

    @Override
    public void insertSystemLog(SystemLog systemLog) {
        systemLogDao.insertSystemLog(systemLog);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<SystemLog> page =  systemLogDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public void delById(Integer id) {
        systemLogDao.delById(id);
    }
}
