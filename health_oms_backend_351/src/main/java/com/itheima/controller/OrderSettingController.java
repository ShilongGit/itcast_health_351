package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    @Log(operationType = "上传操作",operationName = "上传预约设置")
    public Result upload(MultipartFile excelFile){


        try {

            List<String[]> list = POIUtils.readExcel(excelFile);

            List<OrderSetting> orderSettingList = new ArrayList<>();

            for (String[] strings : list) {
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new SimpleDateFormat("yyyy/MM/dd").parse(strings[0]));
                orderSetting.setNumber(Integer.valueOf(strings[1]));
                orderSettingList.add(orderSetting);
            }

            orderSettingService.addOrderSettingData(orderSettingList);

            return new Result(true, MessageConst.IMPORT_ORDERSETTING_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.IMPORT_ORDERSETTING_FAIL);

        }
    }

    @RequestMapping("/findPageData")
    @Log(operationType = "查询操作",operationName = "查询预约设置")
    public Result findPageData(String data){
        return orderSettingService.findPageData(data);
    }


    @RequestMapping("/edit")
    @Log(operationType = "编辑操作",operationName = "编辑预约设置")
    public Result edit(@RequestBody OrderSetting orderSetting){
        return orderSettingService.edit(orderSetting);
    }

}
