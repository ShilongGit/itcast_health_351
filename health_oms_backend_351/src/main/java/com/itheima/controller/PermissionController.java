package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.annotation.Log;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;

import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    PermissionService permissionService;

    @ResponseBody
    @RequestMapping("/add")
    @Log(operationType = "添加操作",operationName = "添加权限")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);

            return new Result(true, "添加权限成功");
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false, "添加权限失败");
        }
    }


    @ResponseBody
    @RequestMapping("/findPage")
    @Log(operationType = "查询操作",operationName = "查询权限")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.findPage(queryPageBean);
        return pageResult;
    }


    @ResponseBody
    @RequestMapping("/delById")
    @Log(operationType = "删除操作",operationName = "删除权限")
    public Result delById(Integer id){
        try {
            permissionService.delById(id);
            return new Result(true, "删除权限成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除权限失败");
        }
    }


    @ResponseBody
    @RequestMapping("/findById")
    @Log(operationType = "查询操作",operationName = "查询权限")
    public Result findById(Integer id){
        try {
            Permission permission = permissionService.findById(id);
            return new Result(true,"查询权限成功", permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询权限失败");
        }
    }


    @ResponseBody
    @RequestMapping("/edit")
    @Log(operationType = "编辑操作",operationName = "编辑权限")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
            return new Result(true,"编辑权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑权限失败");
        }
    }


    @ResponseBody
    @RequestMapping("/findAll")
    @Log(operationType = "查询操作",operationName = "查询权限")
    public Result findAll(){
        try {
            List<Permission> permissionList = permissionService.findAll();
            return new Result(true,"查询权限成功",permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询权限失败");
        }
    }
}
