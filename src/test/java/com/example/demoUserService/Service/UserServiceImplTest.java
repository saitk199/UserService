package com.example.demoUserService.Service;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    Path path = Paths.get("D:\\Projects\\demoUserService\\tmp");
    UserService userService = new UserServiceImpl(path);

    @Test
    void getSizeFolder() {
        Date firstDate = new Date();
        long actual = userService.getSizeFolder();
        Date lastDate = new Date();
        System.out.println("size folder - " + actual + "; time - " + (lastDate.getTime() - firstDate.getTime()));
    }

    @Test
    void deleteFile() {
        String fileName = "test2";
        Date firstDate = new Date();
        userService.deleteFile(fileName);
        Date lastDate = new Date();
        System.out.println("delete file time " + (lastDate.getTime() - firstDate.getTime()));
    }

    @Test
    void deleteAll() {
    }

    @Test
    void zipFolder() {
        String folder = "test3";
        Date firstDate = new Date();
        userService.zipFolder(folder);
        Date lastDate = new Date();
        System.out.println("zip Folder time " + (lastDate.getTime() - firstDate.getTime()));
    }
}