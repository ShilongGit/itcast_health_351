package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Reference
    MenuService menuService;


    @ResponseBody
    @RequestMapping("/add")
    @Log(operationType = "添加操作",operationName = "添加菜单")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);

            return new Result(true, "添加菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.toString());
        }
    }

    @ResponseBody
    @RequestMapping("/findParentMenu")
    @Log(operationType = "查询操作",operationName = "查找菜单")
    public Map<String,Object> findParentMenu(){
        return menuService.findParentMenu();
    }


    @ResponseBody
    @RequestMapping("/findPage")
    @Log(operationType = "查询操作",operationName = "查找菜单")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = menuService.findPage(queryPageBean);
        return pageResult;
    }


    @ResponseBody
    @RequestMapping("/delById")
    @Log(operationType = "删除操作",operationName = "删除菜单")
    public Result delById(Integer id){
        try {
            menuService.delById(id);
            return new Result(true, "删除菜单成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除菜单失败");
        }
    }


    @ResponseBody
    @RequestMapping("/findById")
    @Log(operationType = "查询操作",operationName = "查找菜单")
    public Result findById(Integer id){
        try {
            Menu menu = menuService.findById(id);
            return new Result(true,"查询菜单成功", menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询菜单失败");
        }
    }


    @ResponseBody
    @RequestMapping("/edit")
    @Log(operationType = "编辑操作",operationName = "编辑菜单")
    public Result edit(@RequestBody Menu menu){
        try {
            menuService.edit(menu);
            return new Result(true,"编辑菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑菜单失败");
        }
    }


    @ResponseBody
    @RequestMapping("/findAll")
    @Log(operationType = "查询操作",operationName = "查找菜单")
    public Result findAll(){
        try {
            List<Menu> permissionList = menuService.findAll();
            return new Result(true,"查询菜单成功",permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询菜单失败");
        }
    }

}
