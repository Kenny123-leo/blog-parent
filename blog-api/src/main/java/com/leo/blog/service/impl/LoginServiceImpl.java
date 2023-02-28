package com.leo.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.leo.blog.dao.pojo.SysUser;
import com.leo.blog.service.LoginService;
import com.leo.blog.service.SysUserService;
import com.leo.blog.utils.JWTUtils;
import com.leo.blog.vo.ErrorCode;
import com.leo.blog.vo.Result;
import com.leo.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    //加密盐用于加密
    private static final String slat = "mszlu!@#";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1. 检查参数是否合法
         * 2.根据用户名和密码去user表中查询 是否存在
         * 3.如果不存在 登录失败
         * 4.如果存在， 使用JWT 生成token 返回给前端
         * 5.token放入redis当中，redis token：user信息 设置过期时间
         * （登录认证的时候   先认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        //1. 检查参数是否合法
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            //判空操作
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        // md5加密
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account,password);
        //3.如果不存在 登录失败
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //4.如果存在， 使用JWT 生成token 返回给前端
        String token = JWTUtils.createToken(sysUser.getId());
        //5.token放入redis当中，redis token：user信息 设置过期时间
        // redis问题
        //Redis问题解决-DENIED Redis is running in protected mode because protected mode is enabled, no bind addre
        // 参考：https://blog.csdn.net/chenzz2560/article/details/82756484
        // Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: 参考：https://blog.csdn.net/wwg18895736195/article/details/83628564
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        //返回给前端
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        //将目标数据转成前端所需的JSON
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        // 退出登录，直接在redis中删除token
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在，存在 返回账户已经被注册
         * 3.如果不存在 注册用户
         * 4.生成token   ->生成token的原因：注册完后可直接登录，不用走登录页面
         * 5.存入redis  并返回
         * 6.注意  加上事务，一旦中间的任何过程出现问题，注册的用户  需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        // 判空
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_" + token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);
        return Result.success(token);
    }

    // 获取MD5加密后的密码，用于测试
//    public static void main(String[] args) {
//        String s = DigestUtils.md5Hex("admin" + slat);
//        System.out.println(s);
//    }
}
