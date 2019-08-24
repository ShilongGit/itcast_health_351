package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.pojo.SysUser;
import com.itheima.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Reference
    UserService userService;


    @RequestMapping("/loginSuccess")
    public String loginSuccess(){
        return "redirect:http://localhost:83/pages/main.html";
    }

    @RequestMapping("/loginFail")
    public String loginFail(){
        return "redirect:http://localhost:83/login.html";
    }

    @ResponseBody
    @RequestMapping("/findUserName")
    public Result findUserName(HttpServletRequest request){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();

            if (user == null){
                return new Result(false, "无此用户名");
            }


            request.getSession().setAttribute("user",user.getUsername());

            return new Result(true, "查找用户名成功",user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "无此用户名");
        }
    }

    @ResponseBody
    @RequestMapping("/findMenuByName")
    public Result findMenuByName(String username){
        return userService.findMenuByName(username);
    }
}
