package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConst;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void addOrderSettingData(List<OrderSetting> orderSettingList) {



        for (OrderSetting orderSetting : orderSettingList) {
            orderSettingDao.deleteOrderSettingByDate(orderSetting.getOrderDate());
            orderSettingDao.insertOrderSetting(orderSetting);
        }
    }

    @Override
    public Result findPageData(String data) {

        try {
            Date begin = new SimpleDateFormat("yyyy-MM-dd").parse(data + "-1");
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(data + "-31");

            Map<String , Object> paramMap = new HashMap<>();
            paramMap.put("begin",begin);
            paramMap.put("end",end);

            List<OrderSetting> orderSettingList = orderSettingDao.queryOrderSettingByCondition(paramMap);

            List<Map<String,Object>> resultList = new ArrayList<>();
            for (OrderSetting orderSetting : orderSettingList) {
                Map<String , Object> map = new HashMap<>();
                map.put("date",new SimpleDateFormat("dd").format(orderSetting.getOrderDate()));
                map.put("number",orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());
                resultList.add(map);
            }

            return new Result(true, MessageConst.GET_ORDERSETTING_SUCCESS,resultList);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result(false,MessageConst.GET_ORDERSETTING_FAIL);
        }
    }

    @Override
    public Result edit(OrderSetting orderSetting) {

        try {

            orderSettingDao.updateOrderSetting(orderSetting);

            return new Result(true,MessageConst.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.ORDERSETTING_FAIL);
        }
    }

    @Override
    public void deletePreOrderSetting(String today) {
        orderSettingDao.deletePreOrderSetting(today);
    }
}
