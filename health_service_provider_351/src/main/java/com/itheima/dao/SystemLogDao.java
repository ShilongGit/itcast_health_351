package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.SystemLog;

public interface SystemLogDao {

    void insertSystemLog(SystemLog systemLog);

    Page<SystemLog> findByCondition(String queryString);

    void delById(Integer id);
}
