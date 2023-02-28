package com.leo.blog.service;

import com.leo.blog.dao.pojo.SysUser;
import com.leo.blog.vo.Result;
import com.leo.blog.vo.UserVo;

public interface SysUserService {

    UserVo findUserVOById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存注册用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
