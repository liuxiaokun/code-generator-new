package com.fred.code.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * entity的字段信息封装，里面包含成员名，和成员的数据类型。
 * 例如: private String name;
 *
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/19 9:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class FieldInfo implements Serializable {

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段的数据类型
     */
    private String type;

    /**
     * 字段的长度（主要针对varchar类型）
     */
    private String length;

    /**
     * 是否为空, TRUE 意思是可为空， FALSE意思是不可为空
     */
    private Boolean nullable;


    /**
     * 字段注释
     */
    private String comment;
}
