package com.leo.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class TagVo {
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String tagName;

    private String avatar;
}
