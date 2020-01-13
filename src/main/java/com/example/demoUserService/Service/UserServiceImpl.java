package com.example.demoUserService.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class UserServiceImpl implements UserService {

    private final Path dir;

    public UserServiceImpl(Path path) {
        this.dir = path;
    }

    @Override
    public long getSizeFolder() {

        long sizeDir = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.zip")) {
            for (Path entry : stream) {
                sizeDir += Files.size(entry);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sizeDir;
    }

    @Override
    public void deleteFile(String fileName) {

        Path fileNamePath = Paths.get(dir.toString() + File.separator + fileName + ".csv");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.csv")) {
            for (Path entry : stream) {
                if (entry.toString().equals(fileNamePath.toString())) {
                    Files.delete(entry);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void deleteAll() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.csv")) {
            for (Path entry : stream) {
                Files.delete(entry);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void archivingFile() {
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(dir.toString() + File.separator + "archiveFiles.zip"));
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.csv");
            for (Path entry : stream) {
                FileInputStream fileInputStream = new FileInputStream(entry.toFile());
                ZipEntry zipEntry = new ZipEntry(entry.toFile().getName());
                zipOutputStream.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fileInputStream.read(bytes)) >= 0) {
                    zipOutputStream.write(bytes, 0, length);
                }
                fileInputStream.close();
            }
            zipOutputStream.close();
            deleteAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] download() {
        Path filePath = Paths.get(dir.toString() + File.separator + "archiveFiles.zip");

        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
