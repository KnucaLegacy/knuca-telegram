package com.theopus.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.theopus.schedule.rest.configuration.ScheduleRestConfiguration;

@Configuration
@Import(ScheduleRestConfiguration.class)
@Profile("rest-schedule")
public class ScheduleRestProfile {

}
