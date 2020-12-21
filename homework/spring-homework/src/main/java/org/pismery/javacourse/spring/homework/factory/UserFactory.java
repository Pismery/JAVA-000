package org.pismery.javacourse.spring.homework.factory;

import org.pismery.javacourse.spring.homework.User;

public interface UserFactory {

    default User createUser(String createBy) {
        return new User(1L,"Liuliuliu",createBy);
    }

}
