package com.fred.code.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/21 11:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TableInfo implements Serializable {

    /**
     * 表名字
     */
    private String name;

    /**
     * 表的注释
     */
    private String comment;
}
