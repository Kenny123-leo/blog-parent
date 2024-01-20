package com.leo.blog.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.leo.blog.dao.dos.ChatRequest;
import com.leo.blog.dao.dos.ChatResponse;
import com.leo.blog.vo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {
    @PostMapping("/chat")//文档要求使用post请求
    public Result chat(@RequestBody String q){
        System.out.println("q is ：" + q);
        //这里可以看官方文档
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
        //这里换成自己的ApiKey
        String ApiKey = "sk-8e7731a5b76d43368f9faa4e65b74736";

        //ChatRequest为自定义类
        ChatRequest chatRequest = new ChatRequest(q);
        String json = JSONUtil.toJsonStr(chatRequest);
        //System.out.println(json);//正式发送到api前,测试请求的主要数据情况
        String result = HttpRequest.post(url)
                .header("Authorization","Bearer "+ ApiKey)
                .header("Content-Type","application/json")
                .body(json)
                .execute().body();
        System.out.println(result);

        ChatResponse data = JSONUtil.toBean(result, ChatResponse.class);
        return Result.success(data);

    }


}
