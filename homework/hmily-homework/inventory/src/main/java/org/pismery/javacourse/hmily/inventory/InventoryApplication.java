package org.pismery.javacourse.hmily.inventory;

import org.pismery.javacourse.hmily.inventory.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

// @SpringBootApplication(scanBasePackages="org.pismery.javacourse.hmily")
@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
