package com.example.demoUserService.Service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    Path path = Paths.get("D:\\Projects\\demoUserService\\src\\test\\resources\\tmp");
    UserService userService = new UserServiceImpl(path);


    @Test
    void getSizeFolder() {
        Path target = Paths.get(path.toString() + File.separator + "test.csv");
        try {
            Path file = Files.createFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long expected = 0;
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
        Date firstDate = new Date();
        userService.deleteAll();
        Date lastDate = new Date();
        System.out.println("delete all time " + (lastDate.getTime() - firstDate.getTime()));
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