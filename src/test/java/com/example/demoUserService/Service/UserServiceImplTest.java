package com.example.demoUserService.Service;

import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    Path path;
    UserService userService;

    @BeforeEach
    public void setUp() throws IOException {
        path = Paths.get("src\\test\\resources\\tmp");
        userService = new UserServiceImpl(path);
        Path newFolder = Paths.get(path.toString() + File.separator + "test1");
        FileUtils.writeStringToFile(new File(newFolder.toString() + File.separator + "testData.txt"), "Это тест, и в нем тестируются тесты.", "utf-8");
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
    void zipFolder() throws IOException {
        String folder = "test1";
        userService.zipFolder(folder);
        Path pathDir = Paths.get(path.toString() + File.separator + folder);
        byte[] buffer = new byte[1024];
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(path.toString() + File.separator + folder + ".zip"));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            File newFile = new File(pathDir.toFile(), zipEntry.getName());
            FileUtils.write(newFile, null, "utf-8");
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.close();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
        Path actualFile = Paths.get(pathDir.toFile() + File.separator + "testData.txt");
        FileUtils.writeStringToFile(new File(path.toString() + File.separator + "test2" + File.separator + "testData2.txt"), "Это тест, и в нем тестируются тесты.", "utf-8");
        List<String> links1 = Files.readAllLines(actualFile);
        List<String> links2 = Files.readAllLines(Paths.get(path.toString() + File.separator + "test2" + File.separator + "testData2.txt"));
        for(String line1:links1){
            for(String line2:links2){
                assertTrue(line1.equals(line2));
            }
        }
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