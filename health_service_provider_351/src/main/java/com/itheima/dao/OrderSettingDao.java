package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    void insertOrderSetting(OrderSetting orderSetting);

    List<OrderSetting> queryOrderSettingByCondition(Map<String, Object> paramMap);

    void updateOrderSetting(OrderSetting orderSetting);

    OrderSetting selectOrderSettingByDate(String orderDate);

    void deleteOrderSettingByDate(Date orderDate);

    //定时任务
    void deletePreOrderSetting(String today);
}
