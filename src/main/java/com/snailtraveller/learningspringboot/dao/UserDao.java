package com.snailtraveller.learningspringboot.dao;

import com.snailtraveller.learningspringboot.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    List<User> getAllUsers();
    User getUser(UUID userUid);
    int updateUser(UUID userUid, User user);
    int removeUser(UUID userUid);
    int insertUser(UUID userUid, User user);
}
