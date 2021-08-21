package com.fred.code.generator.util;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/21 15:15
 */
public class DbUtil {

    /**
     * 将数据库的表名翻译成java用的实体名
     * @param tableName
     * @return
     */
    public static String tableNameToEntityName(String tableName) {
        tableName = tableName.toLowerCase();

        if (tableName.contains("t_")) {
            tableName = tableName.substring(2, tableName.length());
        }
        String camelName = "";
        if (tableName.contains("_")) {
            String[] words = tableName.split("_");

            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.length() > 0) {
                    camelName += StringUtil.upperFirstCase(word);
                }
            }
        } else {
            camelName = tableName;
        }
        return StringUtil.upperFirstCase(camelName);
    }
}
