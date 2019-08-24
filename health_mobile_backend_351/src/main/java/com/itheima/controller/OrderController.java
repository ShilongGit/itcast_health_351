package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.model.ModelOrderInfo;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody ModelOrderInfo orderInfo){

        try {

            String validateCode = orderInfo.getValidateCode();
            String dbCode = jedisPool.getResource().get(orderInfo.getTelephone());
            if (!dbCode.equals(validateCode)){
                return new Result(false,MessageConst.VALIDATECODE_ERROR);
            }

            Order order = orderService.addOrder(orderInfo);

            return new Result(true,"SUCCESS",order);
        }catch (RuntimeException re){
            re.printStackTrace();
            return new Result(false, re.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "XXX");
        }
    }

    @RequestMapping("/findById")
    public Map<String,Object> findById(Integer id){
        return orderService.findById(id);
    }
}
