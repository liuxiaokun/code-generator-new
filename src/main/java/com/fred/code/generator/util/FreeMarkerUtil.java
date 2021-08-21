package com.fred.code.generator.util;

import com.fred.code.generator.data.ImportTypeMapping;
import com.fred.code.generator.pojo.FieldInfo;
import com.fred.code.generator.pojo.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.fred.code.generator.util.DatabaseUtil.getTableInfos;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/19 10:19
 */
public class FreeMarkerUtil {

    public static void generateEntity(List<FieldInfo> fields, Map<String, Object> authorInfo, String basePackage,
                                    String entityName, String tableComment) throws Exception {
        Map<String, Object> root = new HashMap<>(10);
        root.putAll(authorInfo);

        root.put("basePackage", basePackage);
        root.put("entityName", entityName);
        root.put("tableComment", tableComment);

        root.put("fieldList", fields);
        //根据 importList来生成importList
        Set<String> importList = new HashSet<>();
        for (FieldInfo field : fields) {
            String importInfo = ImportTypeMapping.mapping.get(field.getType());
            if(null != importInfo) {
                importList.add(importInfo);
            }
        }
        root.put("importList", importList);

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

        List<TableInfo> tableInfos = getTableInfos();
        System.out.println("tableInfos:" + tableInfos);
        for (TableInfo tableInfo : tableInfos) {
            Map<String, Object> author = buildAuthorInfo("liuxiaokun", "1.0.0");
            generateEntity(DatabaseUtil.getColumnInfo(tableInfo.getName()), author, "com.fred.demo",
                    DatabaseUtil.tableNameToEntityName(tableInfo.getName()), tableInfo.getComment());
        }
    }

}