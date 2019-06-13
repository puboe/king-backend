package com.king.gameserver.domain.user;

public interface UserRepository {

    User saveUser(User user);

    void deleteUserById(long userId);

    User getUserById(long userId);
}
