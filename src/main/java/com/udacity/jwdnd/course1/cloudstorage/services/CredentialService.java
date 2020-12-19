package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapping.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class CredentialService {


    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public void addCredentialService(String url, String userName, String credentialUserName, String keyCredential, String password) {
        try{
            Integer userId = userMapper.getUser(userName).getUserId();
            Credential credential = new Credential(0, url, credentialUserName, keyCredential, password, userId);
            credentialMapper.insert(credential);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }

    public void updateCredentialService(Integer credentialId, String newUserName, String url, String keyCredential, String password) {
        try{
            credentialMapper.updateCredential(credentialId, newUserName, url, keyCredential, password);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }

    public Credential getCredentialService(Integer noteId) {
        try{
            return credentialMapper.getCredential(noteId);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }

    public Credential[] getCredentialListingsService(Integer userId) {
        try {
            return credentialMapper.getListCredential(userId);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }
    public void deleteCredentialService(Integer noteId) {
        try {
            credentialMapper.deleteCredential(noteId);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }


}
