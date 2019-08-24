package com.itheima.job;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.Date2Utils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CleanOrderSettingJob {

    @Reference
    OrderSettingService orderSettingService;

    public void clearOrderSetting(){
        try {
            String today = Date2Utils.parseDate2String(new Date());
            orderSettingService.deletePreOrderSetting(today);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
