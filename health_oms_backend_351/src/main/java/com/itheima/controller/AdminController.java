package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import com.itheima.service.AdminService;
import com.itheima.utils.JedisUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Reference
    AdminService adminService;

    @ResponseBody
    @RequestMapping("/clearRedis")
    @Log(operationType = "缓存操作",operationName = "清空缓存")
    public Result clearRedis(){
        try {
            Jedis jedis = JedisUtils.getJedis();
            jedis.flushAll();
            return new Result(true,"缓存刷新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"缓存刷新失败");
        }
    }

    @ResponseBody
    @RequestMapping("/findPageByCondition")
    @Log(operationType = "查询操作",operationName = "查询用户")
    public PageResult findPageByCondition(@RequestBody QueryPageBean queryPageBean){

        return adminService.findPageByCondition(queryPageBean);
    }

    @ResponseBody
    @RequestMapping("/add")
    @Log(operationType = "添加操作",operationName = "添加用户")
    public Result add(@RequestBody SysUser user, Integer[] checkRoleIds){
        return adminService.addUserWithcheckRoleId(user,checkRoleIds);
    }


    @ResponseBody
    @RequestMapping("/findUserById")
    @Log(operationType = "查询操作",operationName = "查询用户")
    public Map<String,Object> findUserById(Integer uid){

        return adminService.findUserById(uid);

    }


    @ResponseBody
    @RequestMapping("/edit")
    @Log(operationType = "编辑操作",operationName = "编辑用户")
    public Result edit(Integer[] checkRoleIds ,@RequestBody SysUser user){
        try {
            adminService.edit(checkRoleIds, user);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }
    }


    @ResponseBody
    @RequestMapping("/delById")
    @Log(operationType = "删除操作",operationName = "删除用户")
    public Result delById(Integer id){
        try {
            adminService.delById(id);
            return new Result(true,"删除用户成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除用户失败");
        }
    }

    @ResponseBody
    @RequestMapping("/findAll")
    @Log(operationType = "查询操作",operationName = "查询用户")
    public List<Role> findAll(){
        return adminService.findAll();
    }

}
