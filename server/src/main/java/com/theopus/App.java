package com.theopus;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(scanBasePackages = "com.theopus.server.configuration",
        exclude = {DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    public DateTimeFormatConfiguration dateTimeFormatConfiguration() {
        return new DateTimeFormatConfiguration();
    }

    public static class DateTimeFormatConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addFormatters(FormatterRegistry registry) {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-d"));
            registrar.registerFormatters(registry);
        }
    }
}
