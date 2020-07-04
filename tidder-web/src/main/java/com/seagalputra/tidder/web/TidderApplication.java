package com.seagalputra.tidder.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = {"com.seagalputra.tidder"})
@EnableJpaRepositories("com.seagalputra.tidder.domain")
@EntityScan("com.seagalputra.tidder.domain")
@EnableAsync
public class TidderApplication {
    public static void main(String[] args) {
        SpringApplication.run(TidderApplication.class, args);
    }
}
