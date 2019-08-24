package com.itheima.service;

import com.itheima.model.ModelOrderInfo;
import com.itheima.pojo.Order;

import java.util.Map;

public interface OrderService {
    Order addOrder(ModelOrderInfo orderInfo);

    Map<String,Object> findById(Integer id);
}
