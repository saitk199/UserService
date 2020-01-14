package com.example.demoUserService.Service;


import java.nio.file.Path;

public interface UserService {

    long getSizeFolder(Path path);

    void deleteFile(String fileName);

    void deleteAll(Path path);

    void archivingFile(String folder);

    byte[] download(String folder);
}
