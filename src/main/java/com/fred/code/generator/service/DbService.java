package com.fred.code.generator.service;

import com.fred.code.generator.pojo.FieldInfo;
import com.fred.code.generator.pojo.TableInfo;

import java.util.List;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/21 14:12
 */
public interface DbService {

    /**
     * 获取数据库下所有的表信息
     *
     * @return @see TableInfo
     */
    List<TableInfo> getTableInfos();


    /**
     * 获取表下面所有的字段信息
     *
     * @return @see FieldInfo
     */
    List<FieldInfo> getColumnInfo(String tableName);
}
