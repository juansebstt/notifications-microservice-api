package com.notificationsmicroserviceapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(stringTemplateResolver());
        return templateEngine;
    }
    @Bean
    public StringTemplateResolver stringTemplateResolver() {
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode("HTML");
        resolver.setCacheable(false);
        return resolver;
    }
}