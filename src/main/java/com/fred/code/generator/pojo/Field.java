package com.fred.code.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

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
public class Field implements Serializable {

    /**
     * 字段的数据类型
     */
    private String type;

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段注释
     */
    private String comment;
}
