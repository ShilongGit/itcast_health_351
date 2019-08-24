package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.JedisUtils;
import com.itheima.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    SetmealService setmealService;

    @RequestMapping("/upload")
    @Log(operationType = "上传操作",operationName = "上传套餐图片")
    public Result upload(MultipartFile uploadFile){

        try {
            //生成一个唯一的文件
            //1. uuid
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //2. 截取后缀名
            //原始文件名
            String originalFilename = uploadFile.getOriginalFilename();
            //截取后缀名
            String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //拼接
            String uploadFileName = uuid + extendName;
            QiniuUtils.upload2Qiniu(uploadFile.getBytes(),uploadFileName);

            Jedis jedis = JedisUtils.getJedis();
            jedis.sadd("add",uploadFileName);
            return new Result(true,"文件上传成功",uploadFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"文件上传失败");
        }
    }

    @RequestMapping("/findPageByCondition")
    @Log(operationType = "查询操作",operationName = "分页查找套餐")
    public PageResult findPageByCondition(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPageByCondition(queryPageBean);
    }

    @RequestMapping("/add")
    @Log(operationType = "添加操作",operationName = "添加套餐")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        return setmealService.addsetmealWithCheckGroup(setmeal,checkgroupIds);
    }
}
