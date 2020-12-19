package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int createUserService(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        try {
            return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }

    public User getUserService(String username) {
        try {
            return userMapper.getUser(username);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }
    public boolean isUsernameFreeService(String username) {
        try {
            return userMapper.getUser(username) == null;
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }
}
