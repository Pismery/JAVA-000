package org.pismery.javacourse.spring.homework.factorybean;

import org.pismery.javacourse.spring.homework.User;
import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new User(1L, "Liuliuliu", "factory-bean");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
