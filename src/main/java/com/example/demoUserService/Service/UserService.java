package com.example.demoUserService.Service;


import java.nio.file.Path;

public interface UserService {

    long getSizeFolder();

    void deleteFile(String fileName);

    void deleteAll();

    void zipFolder(String folder);

    byte[] zipToByte(Path zipPath);
}
