package com.fred.code.generator.util;

import com.fred.code.generator.pojo.Field;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/19 10:19
 */
public class FreeMarkerUtil {

    public static void main(String[] args) throws Exception {
        Map<String, Object> root = new HashMap<>(10);
        root.put("entityPackage","com.fred.demo");
        root.put("author","liuxiaokun");
        root.put("version","1.0.0");
        root.put("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        root.put("entityName","User");
        root.put("tableComment","用户表");

        List<Field> fields = new ArrayList<>();
        fields.add(new Field("String", "name", "姓名"));
        fields.add(new Field("Integer", "age", "年龄"));
        fields.add(new Field("LocalDate", "birthday", "生日"));
        root.put("fieldList", fields);

        List<String> importList = new ArrayList<>();
        importList.add("java.lang.String");
        importList.add("java.lang.Integer");
        importList.add("java.time.LocalDate");
        root.put("importList",importList);

        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(new File("E:/code/idea-code/code-generator/src/main/resources/templates/"));
        config.setObjectWrapper(new DefaultObjectWrapper());
        Template template = config.getTemplate("entity.ftl");

        File file = new File("E:/User.java");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        template.process(root, bw);
        bw.flush();
        fileWriter.close();
    }

}