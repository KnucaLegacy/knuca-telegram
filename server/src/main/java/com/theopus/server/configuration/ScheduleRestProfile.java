package com.theopus.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.theopus.schedule.rest.configuration.ScheduleRestConfiguration;

@Configuration
@EnableWebMvc
@Import(ScheduleRestConfiguration.class)
@Profile("rest-schedule")
public class ScheduleRestProfile {

}
