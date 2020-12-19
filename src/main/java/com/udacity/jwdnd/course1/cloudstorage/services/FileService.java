package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapping.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.data.File;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {

    private final UserMapper userMapper;
    private final FileMapper fileMapper;

    public FileService(UserMapper userMapper, FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public String[] getFileListService(Integer userId) {
        try {
            return fileMapper.getListFile(userId);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }
    public File getFileService(String fileName) {
        try {
            return fileMapper.getFile(fileName);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }
    public void deleteFileService(String fileName) {
        try {
            fileMapper.deleteFile(fileName);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }

    public void addFileService(String userName, MultipartFile multipartFile) throws IOException {

        byte[] dataToRead = new byte[1024];
        int cursorRead;
        InputStream inputStream = multipartFile.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while ((cursorRead = inputStream.read(dataToRead, 0, dataToRead.length)) != -1) {
            byteArrayOutputStream.write(dataToRead, 0, cursorRead);
        }
        byteArrayOutputStream.flush();
        byte[] fileData = byteArrayOutputStream.toByteArray();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = userMapper.getUser(userName).getUserId();
        String contentType = multipartFile.getContentType();
        String fileName = multipartFile.getOriginalFilename();

        File file = new File(0, fileName, contentType, fileSize, userId, fileData);
        try {
            fileMapper.insertFile(file);
        }catch (PersistenceException e) {
            throw new PersistenceException(e);
        }

    }
}
