package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {
    void addOrderSettingData(List<OrderSetting> orderSettingList);

    Result findPageData(String data);

    Result edit(OrderSetting orderSetting);

    void deletePreOrderSetting(String today);
}
