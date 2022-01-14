package io.github.mat3e.controller;

import io.github.mat3e.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InfoController {

    private DataSourceProperties url;
    private TaskConfigurationProperties taskConfigurationProperties;


    public InfoController(DataSourceProperties url, TaskConfigurationProperties taskConfigurationProperties) {
        this.url = url;
        this.taskConfigurationProperties = taskConfigurationProperties;
    }

    @GetMapping("/info/url")
    String url() {
        return this.url.getUrl();
    }

    @GetMapping("/info/prop")
    boolean myProp() {
        return this.taskConfigurationProperties.getTemplate().isAllowMultipleTasksFromTemplate();
    }
}
