package com.example.demoUserService.Service;

import java.nio.file.Path;

public interface UserService {

    long getSizeFolder();

    void deleteFile(String fileName);

    void deleteAll();

    byte[] download(Path fileNamePath);
}
