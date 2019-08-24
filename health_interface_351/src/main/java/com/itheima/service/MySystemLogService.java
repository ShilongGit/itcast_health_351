package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SystemLog;

public interface MySystemLogService {

    void insertSystemLog(SystemLog systemLog);

    PageResult findPage(QueryPageBean queryPageBean);

    void delById(Integer id);
}
