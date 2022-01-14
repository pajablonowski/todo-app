package io.github.mat3e;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
        prefix = "task"
)
public class TaskConfigurationProperties {


    private Template template;


    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
    @Configuration
    @ConfigurationProperties
    public static class Template {
        private boolean allowMultipleTasks;

        public boolean isAllowMultipleTasks() {
            return allowMultipleTasks;
        }

        public void setAllowMultipleTasks(boolean allowMultipleTasks) {
            this.allowMultipleTasks = allowMultipleTasks;
        }
    }
}
