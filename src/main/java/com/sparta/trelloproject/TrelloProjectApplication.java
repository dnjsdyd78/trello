package com.sparta.trelloproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class TrelloProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloProjectApplication.class, args);
    }

}
