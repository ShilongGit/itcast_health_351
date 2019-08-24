package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Reference
    MemberService memberService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/smsLogin")
    public Result smsLogin(@RequestBody Map<String,String> map){

        try {
            String telephone = map.get("telephone");
            String validateCode = map.get("validateCode");
            String redisTelphone = jedisPool.getResource().get(telephone);

            if (!validateCode.equals(redisTelphone)){
                return new Result(false, MessageConst.VALIDATECODE_ERROR);
            }

            memberService.smsLogin(map);

            return new Result(true,MessageConst.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"登陆失败");
        }
    }
}
