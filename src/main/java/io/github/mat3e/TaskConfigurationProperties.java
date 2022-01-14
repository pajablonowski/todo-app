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
        if (template == null) {
            template = new Template();
        }
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public static class Template {
        private boolean allowMultipleTasks;


        public boolean isAllowMultipleTasksFromTemplate() {
            return allowMultipleTasks;
        }

        public void setAllowMultipleTasksFromTemplate(boolean allowMultipleTasksFromTemplate) {
            this.allowMultipleTasks = allowMultipleTasksFromTemplate;
        }
    }
}
