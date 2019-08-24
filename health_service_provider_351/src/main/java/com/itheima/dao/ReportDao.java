package com.itheima.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao {
    List<Map<String,Object>> getSetmealReport();

    List<Map<String,Object>> selectHotSetmeal();
}
