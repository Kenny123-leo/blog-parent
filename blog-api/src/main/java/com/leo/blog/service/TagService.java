package com.leo.blog.service;

import com.leo.blog.vo.Result;
import com.leo.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签的limit条
     * @param limit
     * @return
     */
    Result hots(int limit);

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findAllDetailById(Long id);
}
