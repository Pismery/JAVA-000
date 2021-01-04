package org.pismery.javacourse.hmily.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AccountApplication.class);
        // application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

}
