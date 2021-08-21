package com.fred.code.generator.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/21 13:48
 */
@ConfigurationProperties(prefix="project")
@Component
@Data
@ToString
public class Project {

    private String name;

    private String author;

    private String version;

    private DataSource datasource;
}
