package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConst;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.model.ModelOrderInfo;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    @Override
    @Transactional
    public Order addOrder(ModelOrderInfo orderInfo) {

        OrderSetting orderSetting = orderSettingDao.selectOrderSettingByDate(orderInfo.getOrderDate());

        if (orderSetting.getNumber() <= orderSetting.getReservations()){
            throw new RuntimeException(MessageConst.ORDER_FULL);
        }

        Member member = memberDao.selectMemberByTelephone(orderInfo.getTelephone());

        if (member == null){
            member = new Member();
            member.setName(orderInfo.getName());
            //sex :性别
            member.setSex(orderInfo.getSex());
            //idCard :身份证号
            member.setIdCard(orderInfo.getIdCard());
            // phoneNumber : 手机号码
            member.setPhoneNumber(orderInfo.getTelephone());
            // regTime: 注册时间（当前时间）
            member.setRegTime(new Date());

            memberDao.insertMember(member);
        }else {
            Map<String,Object> paramMap = new HashMap<>();

            paramMap.put("memberId",member.getId());
            paramMap.put("orderDate",orderInfo.getOrderDate());
            paramMap.put("setmealId",orderInfo.getSetmealId());

            Order order = orderDao.selectOrderByCondition(paramMap);

            if (order != null){
                throw new RuntimeException(MessageConst.HAS_ORDERED);
            }
        }

        Date parse = null;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd").parse(orderInfo.getOrderDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        order.setOrderDate(parse);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.valueOf(orderInfo.getSetmealId()));

        orderDao.insertOrder(order);

        orderSetting.setReservations(orderSetting.getReservations()+1);

        orderSettingDao.updateOrderSetting(orderSetting);


        return order;
    }

    @Override
    public Map<String,Object> findById(Integer id) {
        Map<String,Object> resultMap = orderDao.selectOrderById(id);

        Date orderDate = (Date) resultMap.get("orderDate");
        try {
            String s = DateUtils.parseDate2String(orderDate, "yyyy-MM-dd");
            resultMap.put("orderDate",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }
}
