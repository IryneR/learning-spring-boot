package com.snailtraveller.learningspringboot.dao;

import com.snailtraveller.learningspringboot.model.User;

import java.util.*;

public class FakeDataDao implements UserDao{

    private static Map<UUID, User> database;

    static {
        database = new HashMap<>();
        UUID joeUserUid = UUID.randomUUID();
        User joe = new User(joeUserUid,"joe","jona", User.Gender.MALE,22,"joe@gmail.com");
        database.put(joeUserUid, joe);
    }
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public User getUser(UUID userUid) {
        return database.get(userUid);
    }

    @Override
    public int updateUser(UUID userUid, User user) {
        database.put(userUid, user);
        return 1;
    }

    @Override
    public int removeUser(UUID userUid) {
        database.remove(userUid);
        return 1;
    }

    @Override
    public int insertUser(UUID userUid, User user) {
        database.put(userUid, user);
        return 1;
    }
}
