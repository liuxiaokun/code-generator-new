package com.fred.code.generator.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/19 19:28
 */
public class ImportTypeMapping {

    public static final Map<String, String> mapping = new HashMap<>();

    static {
        mapping.put("String", "java.lang.String");
        mapping.put("Integer", "java.lang.Integer");
        mapping.put("LocalDate", "java.time.LocalDate");
        mapping.put("Double", "java.lang.Double");
        mapping.put("Long", "java.lang.Long");
        mapping.put("BigDecimal", "java.math.BigDecimal");
        mapping.put("Boolean", "java.lang.Boolean");
        mapping.put("LocalTime", "java.time.LocalTime");
    }

}
