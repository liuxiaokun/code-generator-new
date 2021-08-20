package com.fred.code.generator.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/19 19:28
 */
public class ImportTypeMapping {

    private String[] imports = {"java.lang.String", "java.lang.Integer", "java.time.LocalDate", "java.lang.Double",
            "java.lang.Long", "java.math.BigDecimal","java.lang.Boolean","java.time.LocalTime"};

    private String[] types = {"String", "Integer", "LocalDate", "Double", "Long", "BigDecimal", "Boolean", "LocalTime"};

    private static Map<String, Integer> mapping = new HashMap<>();

    static {
        mapping.put("varchar", 0);
        mapping.put("int", 1);
        mapping.put("date", 2);
        mapping.put("datetime", 3);
        mapping.put("double", 4);
        mapping.put("bigint", 5);
        mapping.put("decimal", 5);
        mapping.put("tinyint(1)", 6);
        mapping.put("time", 7);
    }
}
