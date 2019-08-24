package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Controller
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;

    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @ResponseBody
    @RequestMapping("/add")
    @Log(operationType = "添加操作",operationName = "添加检查项")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem); // ctrl + alt + T
            //操作成功
            return new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败
            return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @ResponseBody
    @RequestMapping("/findPage")
    @Log(operationType = "查询操作",operationName = "查询检查项")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        return pageResult;
    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @ResponseBody
    @RequestMapping("/delById")
    @Log(operationType = "删除操作",operationName = "删除检查项")
    public Result delById(Integer id){
        try {
            checkItemService.delById(id);
            return new Result(true, MessageConst.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.DELETE_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @ResponseBody
    @RequestMapping("/findById")
    @Log(operationType = "查询操作",operationName = "查询检查项")
    public Result findById(Integer id){
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,MessageConst.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @ResponseBody
    @RequestMapping("/edit")
    @Log(operationType = "编辑操作",operationName = "编辑检查项")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConst.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.EDIT_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @ResponseBody
    @RequestMapping("/findAll")
    @Log(operationType = "查询操作",operationName = "查询检查项")
    public Result findAll(){
        try {
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true,MessageConst.EDIT_CHECKITEM_SUCCESS,checkItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.EDIT_CHECKITEM_FAIL);
        }
    }



}
