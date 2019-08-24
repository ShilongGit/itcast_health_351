package com.itheima.controller;

import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){

        try {

            Integer code = ValidateCodeUtils.generateValidateCode(4);

            SMSUtils.sendShortMessage(telephone,code+"");

            jedisPool.getResource().setex(telephone,60,String.valueOf(code));

            return new Result(true,MessageConst.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
        }
    }


    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){

        try {

            Integer code = ValidateCodeUtils.generateValidateCode(6);

            SMSUtils.sendShortMessage(telephone,String.valueOf(code));

            jedisPool.getResource().setex(telephone,60,code+"");

            return new Result(true,MessageConst.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
        }
    }
}
