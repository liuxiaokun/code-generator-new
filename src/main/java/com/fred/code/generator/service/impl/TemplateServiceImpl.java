package com.fred.code.generator.service.impl;

import com.fred.code.generator.data.ImportTypeMapping;
import com.fred.code.generator.pojo.FieldInfo;
import com.fred.code.generator.pojo.Project;
import com.fred.code.generator.pojo.TableInfo;
import com.fred.code.generator.service.DbService;
import com.fred.code.generator.service.TemplateService;
import com.fred.code.generator.service.impl.DbServiceImpl;
import com.fred.code.generator.util.DbUtil;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/19 10:19
 */
@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private Project project;

    @Autowired
    private DbService dbService;

    @Override
    public void generateEntity() {
        dbService.getTableInfos().forEach(tem -> {
            try {
                generateEntity(tem);
            } catch (Exception e) {
                log.error("error", e);
            }
        });
    }

    private void generateEntity(TableInfo tableInfo) throws Exception {
        Map<String, Object> root = new HashMap<>(10);
        root.putAll(buildAuthorInfo());

        String entityName = DbUtil.tableNameToEntityName(tableInfo.getName());
        root.put("basePackage", project.getBasePackage());
        root.put("entityName", entityName);
        root.put("tableComment", tableInfo.getComment());

        List<FieldInfo> fields = dbService.getColumnInfo(tableInfo.getName());
        root.put("fieldList", fields);
        //根据 importList来生成importList
        Set<String> importList = new HashSet<>();
        for (FieldInfo field : fields) {
            String importInfo = ImportTypeMapping.mapping.get(field.getType());
            if (null != importInfo) {
                importList.add(importInfo);
            }
        }
        root.put("importList", importList);

        generateFile("entity.ftl", project.getOutputDir() + entityName + ".java", root);
    }

    private void generateFile(String ftlFile, String generatedFilePath, Map<String, Object> params) throws Exception {
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(new File(project.getTemplateDir()));
        config.setObjectWrapper(new DefaultObjectWrapper());
        Template template = config.getTemplate(ftlFile);

        File file = new File(generatedFilePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        template.process(params, bw);
        bw.flush();
        fileWriter.close();
    }

    private Map<String, Object> buildAuthorInfo() {
        Map<String, Object> authorInfo = new HashMap<>(10);
        authorInfo.put("author", project.getAuthor());
        authorInfo.put("version", project.getVersion());
        authorInfo.put("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return authorInfo;
    }
}