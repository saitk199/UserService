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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    void zipFolder() throws IOException {
        String folder = "test1";
        userService.zipFolder(folder);
        Path pathDir = Paths.get(path.toString() + File.separator + folder);
        byte[] buffer = new byte[1024];
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(path.toString() + File.separator + folder + ".zip"));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(pathDir.toFile(), zipEntry);
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
        File file = new File(path.toString() + File.separator + folder + File.separator + "testData.txt");
        FileUtils.writeStringToFile(new File(path.toString() + File.separator + "testData2.txt"), "Это тест, и в нем тестируются тесты.", "utf-8");

    }

    public static File newFile(File pathDir, ZipEntry zipEntry) throws IOException {
        File newFile = new File(pathDir, zipEntry.getName());

        String destDirPath = pathDir.getCanonicalPath();
        String destFilePath = newFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return newFile;
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