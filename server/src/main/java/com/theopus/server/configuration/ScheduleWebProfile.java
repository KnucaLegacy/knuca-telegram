package com.theopus.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
@Profile("web-schedule")
public class ScheduleWebProfile {

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addRedirectViewController("/", "/index.html");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                super.addResourceHandlers(registry);
                registry
                        .addResourceHandler("/**.html",
                                "/**.json",
                                "/**.ico",
                                "/**.js")
                        .addResourceLocations("classpath:/static/");
//                        .addResourceLocations("classpath:/static/index.html");
                registry
                        .addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/static/static/");
            }


        };
    }
}
