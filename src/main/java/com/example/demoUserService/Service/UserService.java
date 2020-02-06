package com.example.demoUserService.Service;


import java.nio.file.Path;
import java.util.List;

public interface UserService {

    long getSizeFolder();

    void deleteFile(String fileName);

    void deleteAll();

    void zipFolder(String folder);

    byte[] zipToByte(Path zipPath);

    List<String> getCodeByZipNames();

    List<String> getCodeByFolder();
}
