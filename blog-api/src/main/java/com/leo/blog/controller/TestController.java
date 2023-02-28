package com.leo.blog.controller;

import com.leo.blog.dao.pojo.SysUser;
import com.leo.blog.utils.UserThreadLocal;
import com.leo.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
