package org.pismery.javacourse.ss.homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("org.pismery.javacourse.ss.homework.dao")
public class SsHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsHomeworkApplication.class, args);
    }
}
