package com.leo.blog.dao.pojo;

import lombok.Data;

@Data
public class ArticleBody {

    private Long id;
    private String content;   // 文章内容Markdown语法
    private String contentHtml;
    private Long articleId;
}
