package com.fred.code.generator.controller;

import com.fred.code.generator.pojo.Project;
import com.fred.code.generator.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
 
    @Autowired
    public Project project;

    @Autowired
    private TemplateService templateService;
 
    //访问/hello或者/hi任何一个地址，都会返回一样的结果
    @GetMapping(value = "/hello")
    public String say(){
        templateService.generateEntity();
        return project.toString();
    }
 
 
}