package com.fred.code.generator.util;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/21 10:21
 */
public class StringUtil {


    /**
     * 将字符串的首字母大写
     * @param str
     * @return
     */
    public static String upperFirstCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static String lowerFirstCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    public static void main(String[] args) {
        System.out.println(upperFirstCase("hello"));
    }
}

