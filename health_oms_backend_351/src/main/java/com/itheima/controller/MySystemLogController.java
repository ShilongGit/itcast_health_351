package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.MySystemLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/log")
public class MySystemLogController {

    @Reference
    MySystemLogService systemLogService;


    @ResponseBody
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = systemLogService.findPage(queryPageBean);
        return pageResult;
    }

    @ResponseBody
    @RequestMapping("/delById")
    public Result delById(Integer id){
        try {
            systemLogService.delById(id);
            return new Result(true, "删除日志成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除日志失败");
        }
    }


}
