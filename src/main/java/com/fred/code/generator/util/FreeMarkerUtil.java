package com.fred.code.generator.util;

import com.fred.code.generator.pojo.Field;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    public static void generateEntity(List<Field> fields, Map<String, Object> authorInfo, String basePackage,
                                    String entityName, String tableComment) throws Exception {
        Map<String, Object> root = new HashMap<>(10);
        root.putAll(authorInfo);

        root.put("entityPackage", basePackage);
        root.put("entityName", entityName);
        root.put("tableComment", tableComment);

        root.put("fieldList", fields);
        //根据 importList来生成importList
        //root.put("importList", importList);

        generateFile("entity.ftl", "E:/" + entityName + ".java", root);
    }

    private static void generateFile(String ftlFile, String generatedFilePath, Map<String, Object> params) throws Exception {
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(new File("E:/code/idea-code/code-generator/src/main/resources/templates/"));
        config.setObjectWrapper(new DefaultObjectWrapper());
        Template template = config.getTemplate(ftlFile);

        File file = new File(generatedFilePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        template.process(params, bw);
        bw.flush();
        fileWriter.close();
    }

    public static Map<String, Object> buildAuthorInfo(String author, String version) {
        Map<String, Object> authorInfo = new HashMap<>(10);
        authorInfo.put("author", author);
        authorInfo.put("version", version);
        authorInfo.put("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return authorInfo;
    }


    public static void main(String[] args) throws Exception {

        List<String> importList = new ArrayList<>();
        importList.add("java.lang.String");
        importList.add("java.lang.Integer");
        importList.add("java.lang.Double");
        importList.add("java.time.LocalDate");


        //generateEntity(DatabaseUtil.getColumnInfo("t_user"), importList,)
    }

}