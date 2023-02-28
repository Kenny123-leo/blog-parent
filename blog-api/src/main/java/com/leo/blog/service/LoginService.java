package com.leo.blog.service;

import com.leo.blog.dao.pojo.SysUser;
import com.leo.blog.vo.Result;
import com.leo.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

// 添加事务，出现错误就进行回滚防止添加异常
@Transactional
public interface LoginService {

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
