package com.udacity.jwdnd.course1.cloudstorage.data;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {

    private MultipartFile file;

    public FileForm(MultipartFile file) {
        this.file = file;
    }

    public FileForm() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
