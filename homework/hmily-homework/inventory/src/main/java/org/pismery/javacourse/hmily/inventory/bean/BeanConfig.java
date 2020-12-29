package org.pismery.javacourse.hmily.inventory.bean;

import org.pismery.javacourse.hmily.account.api.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // @Bean
    public AccountService accountService() {
        // return new AccountServiceImpl();
        return null;
    }

}
