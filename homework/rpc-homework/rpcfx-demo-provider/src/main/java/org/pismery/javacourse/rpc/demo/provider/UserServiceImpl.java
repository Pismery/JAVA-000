package org.pismery.javacourse.rpc.demo.provider;

import org.pismery.javacourse.rpc.demo.api.User;
import org.pismery.javacourse.rpc.demo.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
