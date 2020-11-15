package org.pismery.javacourse.springhomework.factory;

import org.pismery.javacourse.springhomework.User;

public interface UserFactory {

    default User createUser(String createBy) {
        return new User(1L,"Liuliuliu",createBy);
    }

}
