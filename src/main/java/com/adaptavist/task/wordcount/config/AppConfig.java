package com.adaptavist.task.wordcount.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Tika tika() {
        return new Tika();
    }
}
