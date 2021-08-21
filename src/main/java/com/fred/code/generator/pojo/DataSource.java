package com.fred.code.generator.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaokun
 * @version 1.0.0
 * @date 2021/8/21 13:42
 */
@Component
@Data
@ToString
public class DataSource {

    private String driver;

    private String url;

    private String username;

    private String password;
}
