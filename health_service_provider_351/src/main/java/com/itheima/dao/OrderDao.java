package com.itheima.dao;

import com.itheima.model.ModelOrderInfo;
import com.itheima.pojo.Order;

import java.util.Map;

public interface OrderDao {
    Order selectOrderByCondition(Map<String, Object> paramMap);

    void insertOrder(Order order);

    Map<String,Object> selectOrderById(Integer id);

    long selectTodayOrderNumber(String reportDate);

    long selectTodayVisitsNumber(String reportDate);

    long selectOrderNumberWithStartEnd(String begin, String end);

    long selectVisitsNumber(String firstDayOfWeek);
}
