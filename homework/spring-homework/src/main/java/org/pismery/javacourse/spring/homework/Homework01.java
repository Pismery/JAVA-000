package org.pismery.javacourse.spring.homework;

import org.pismery.javacourse.spring.homework.factory.DefaultUserFactory;
import org.pismery.javacourse.spring.homework.factory.UserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ServiceLoader;


/**
 * Homework for create bean
 */
public class Homework01 {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/user-bean.xml");

        User userByConstructMethod = (User) context.getBean("user-by-construct-method");
        System.out.println(userByConstructMethod);

        User beanByInitMethod = (User) context.getBean("user-by-init-method");
        System.out.println(beanByInitMethod);

        User userByStaticFactoryMethod = (User) context.getBean("user-by-static-factory-method");
        System.out.println(userByStaticFactoryMethod);

        User userByInstanceFactoryMethod = (User) context.getBean("user-by-instance-factory-method");
        System.out.println(userByInstanceFactoryMethod);

        User userByFactoryBean = (User) context.getBean("user-by-factory-bean");
        System.out.println(userByFactoryBean);

        ServiceLoader<UserFactory> serviceLoader = context.getBean("user-by-service-loader", ServiceLoader.class);
        for (UserFactory userFactory : serviceLoader) {
            System.out.println(userFactory.createUser("service-loader"));
        }

        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        DefaultUserFactory defaultUserFactory = beanFactory.getBean(DefaultUserFactory.class);
        System.out.println(defaultUserFactory.createUser("autowire-capable-bean-factory"));
    }

}
