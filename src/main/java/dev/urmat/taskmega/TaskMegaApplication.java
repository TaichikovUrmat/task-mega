package dev.urmat.taskmega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TaskMegaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskMegaApplication.class, args);
    }

}
