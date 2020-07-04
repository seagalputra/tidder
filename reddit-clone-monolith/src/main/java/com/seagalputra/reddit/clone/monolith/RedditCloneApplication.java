package com.seagalputra.reddit.clone.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.seagalputra.reddit.clone"})
public class RedditCloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedditCloneApplication.class, args);
    }
}
