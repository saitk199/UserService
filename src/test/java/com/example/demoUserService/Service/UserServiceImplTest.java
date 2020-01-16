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

        long expected = 2382;
        Date firstDate = new Date();
        long actual = userService.getSizeFolder();
        Date lastDate = new Date();
        if(expected == actual){
            System.out.println(lastDate.getTime() - firstDate.getTime());
        }
    }

    @Test
    void deleteAll() {
    }

    @Test
    void archivingFile() {
    }
}