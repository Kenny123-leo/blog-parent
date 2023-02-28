package com.leo.blog.controller;

import com.leo.blog.service.LoginService;
import com.leo.blog.service.SysUserService;
import com.leo.blog.vo.Result;
import com.leo.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

//    @Autowired
//    private SysUserService sysUserService;  这个service就只是负责user表相关操作    单一职责原则
    @Autowired
    private LoginService loginService;


    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登录 验证用户  访问用户表，但是
       return loginService.login(loginParam);
    }

}
