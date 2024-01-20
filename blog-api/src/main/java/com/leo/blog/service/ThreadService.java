package com.leo.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.leo.blog.dao.mapper.ArticleMapper;
import com.leo.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    //期望此操作再线程池 执行 不会影响原有的主线程
    //这里线程池不了解可以去看JUC并发编程
    @Async("taskExecutor")
    public void updateAtricleViewCount(ArticleMapper articleMapper, Article article) {

        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        // 设置一个 为了再多线程的环境下  线程安全 
        // 在修改时再查一次，如果没被修改再执行+1  类似于CAS操作 cas加自旋，加个循环就是cas
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        // update article set view_count = 100 where view_count = 99 and id=11
        articleMapper.update(articleUpdate,updateWrapper);
        try {
            Thread.sleep(5000);
            System.out.println("更新完成了。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
