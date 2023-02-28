package com.leo.blog.service;

import com.leo.blog.vo.CategoryVo;
import com.leo.blog.vo.Result;

import java.util.List;

public interface CategoryService {

    /**
     * 根据id获取类别
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 获取所有的类别
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findAllDetailById(Long id);
}
