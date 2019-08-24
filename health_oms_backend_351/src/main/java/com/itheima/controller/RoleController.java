package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Reference
    RoleService roleService;

    @ResponseBody
    @RequestMapping("/findPageByCondition")
    @Log(operationType = "查询操作",operationName = "分页查找角色")
    public PageResult findPageByCondition(@RequestBody QueryPageBean queryPageBean){

        return roleService.findPageByCondition(queryPageBean);
    }

    @ResponseBody
    @RequestMapping("/add")
    @Log(operationType = "添加操作",operationName = "添加角色")
    public Result add(@RequestBody Role role, Integer[] checkMenuIds,Integer[] checkPermissionIds){
        return roleService.addRoleWithConditionIds(role,checkMenuIds,checkPermissionIds);
    }


    @ResponseBody
    @RequestMapping("/findUserById")
    @Log(operationType = "查询操作",operationName = "查找角色")
    public Map<String,Object> findUserById(Integer id){

        return roleService.findUserById(id);

    }


    @ResponseBody
    @RequestMapping("/edit")
    @Log(operationType = "编辑操作",operationName = "编辑角色")
    public Result edit(Integer[] checkMenuIds ,Integer[] checkPermissionIds ,@RequestBody Role role){
        try {
            roleService.edit(role, checkMenuIds,checkPermissionIds);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }
    }


    @ResponseBody
    @RequestMapping("/delById")
    @Log(operationType = "删除操作",operationName = "删除角色")
    public Result delById(Integer id){
        try {
            roleService.delById(id);
            return new Result(true,"删除角色成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除角色失败");
        }
    }

    @ResponseBody
    @RequestMapping("/findAll")
    @Log(operationType = "查询操作",operationName = "查找全部角色")
    public Map<String,Object> findAll(){
        return roleService.findAll();
    }
}
