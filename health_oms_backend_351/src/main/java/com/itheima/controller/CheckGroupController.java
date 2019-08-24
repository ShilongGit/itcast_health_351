package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;

    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @ResponseBody
    @RequestMapping("/findPageByCondition")
    @Log(operationType = "查询操作",operationName = "查询检查组")
    public PageResult findPageByCondition(@RequestBody QueryPageBean queryPageBean){

        return checkGroupService.findPageByCondition(queryPageBean);
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    @ResponseBody
    @RequestMapping("/add")
    @Log(operationType = "添加操作",operationName = "添加检查组")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        return checkGroupService.addCheckGroupWithCheckItemId(checkGroup,checkitemIds);
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @ResponseBody
    @RequestMapping("/findCheckGroupById")
    @Log(operationType = "查询操作",operationName = "查询检查组")
    public Map<String,Object> findCheckGroupById(Integer gid){

        return checkGroupService.findCheckGroupById(gid);

    }

    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @ResponseBody
    @RequestMapping("/edit")
    @Log(operationType = "编辑操作",operationName = "编辑检查组")
    public Result edit(Integer[] checkitemIds ,@RequestBody CheckGroup checkGroup){
        try {
            checkGroupService.edit(checkitemIds, checkGroup);
            return new Result(true, MessageConst.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.EDIT_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    @ResponseBody
    @RequestMapping("/delById")
    @Log(operationType = "删除操作",operationName = "删除检查组")
    public Result delById(Integer id){
        try {
            checkGroupService.delById(id);
            return new Result(true,MessageConst.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.DELETE_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @ResponseBody
    @RequestMapping("/findAll")
    @Log(operationType = "查询操作",operationName = "查询检查组")
    public List<CheckGroup> findAll(){
        return checkGroupService.findAll();
    }
}
