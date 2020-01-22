package com.example.demoUserService.Service;

import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    Path path;
    UserService userService;

    @BeforeEach
    public void setUp() {
        path = Paths.get("src\\test\\resources\\tmp");
        userService = new UserServiceImpl(path);
        Path newFolder = Paths.get(path.toString() + File.separator + "test1");
        try {
            FileUtils.writeStringToFile(new File(newFolder.toString() + File.separator + "testData.txt"), "Это тест, и в нем тестируются тесты.", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getSizeFolder() {
        Path newFolder2 = Paths.get(path.toString() + File.separator + "test2");
        try {
            FileUtils.writeStringToFile(new File(newFolder2.toString() + File.separator + "testData2.txt"), "Это второй тест.", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long expected = 93;
        long actual = userService.getSizeFolder();
        assertEquals(expected, actual);
    }

    @Test
    void deleteFolder(){
        String fileName = "test1";
        userService.deleteFile(fileName);
        Path folderPath = Paths.get(path.toString() + File.separator + fileName);
        assertFalse(folderPath.toFile().exists());
    }

    @Test
    void deleteFile() {
        String fileName = "test1\\testData.txt";
        userService.deleteFile(fileName);
        File testFile = new File(path.toString() + File.separator + fileName);
        assertFalse(testFile.exists());
    }

    @Test
    void deleteAll() {
        Path newFolder2 = Paths.get(path.toString() + File.separator + "test2");
        try {
            FileUtils.writeStringToFile(new File(newFolder2.toString() + File.separator + "testData2.txt"), "Это второй тест.", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        userService.deleteAll();
        assertFalse(path.toFile().exists());
    }

    @Test
    void zipFolder() {
        String folder = "test1";
        Date firstDate = new Date();
        userService.zipFolder(folder);
        Date lastDate = new Date();

        System.out.println("zip Folder time " + (lastDate.getTime() - firstDate.getTime()));
    }

    @AfterEach
    public void clean() {
        try {
            FileUtils.deleteDirectory(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}