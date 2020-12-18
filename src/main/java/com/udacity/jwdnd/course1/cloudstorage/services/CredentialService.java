package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapping.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {


    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public void addCredentialService(String url, String userName, String credentialUserName, String keyCredential, String password) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Credential credential = new Credential(0, url, credentialUserName, keyCredential, password, userId);
        credentialMapper.insert(credential);
    }

    public void updateCredentialService(Integer credentialId, String newUserName, String url, String keyCredential, String password) {
        credentialMapper.updateCredential(credentialId, newUserName, url, keyCredential, password);
    }

    public Credential getCredentialService(Integer noteId) {
        return credentialMapper.getCredential(noteId);
    }

    public Credential[] getCredentialListingsService(Integer userId) {
        return credentialMapper.getListCredential(userId);
    }
    public void deleteCredentialService(Integer noteId) {
        credentialMapper.deleteCredential(noteId);
    }


}
