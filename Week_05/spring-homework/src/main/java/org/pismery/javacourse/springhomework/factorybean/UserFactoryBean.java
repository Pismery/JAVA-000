package org.pismery.javacourse.springhomework.factorybean;

import org.pismery.javacourse.springhomework.User;
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
