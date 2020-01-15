package com.example.demoUserService.Service;

import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class UserServiceImpl implements UserService {

    private final Path dir;

    public UserServiceImpl(Path path) {
        this.dir = path;
    }

    @Override
    public long getSizeFolder(Path path) {

        long sizeDir = FileUtils.sizeOfDirectory(path.toFile());
        return sizeDir;

        /*try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {

            for (Path entry : stream) {
                if (entry.toFile().isFile()) {
                    sizeDir += Files.size(entry);
                } else {
                    sizeDir += getSizeFolder(entry);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sizeDir;*/

/*
        final AtomicLong size = new AtomicLong(0);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {

                    System.out.println("skipped: " + file + " (" + exc + ")");
                    // Skip folders that can't be traversed
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

                    if (exc != null)
                        System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                    // Ignore errors traversing a folder
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }

        return size.get();
*/
    }

    @Override
    public void deleteFile(String fileName) {
        File file = new File(dir.toString() + File.separator + fileName);
        try {
            if(file.isFile()){
                FileUtils.forceDelete(file);
            }else {
                FileUtils.deleteDirectory(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

/*
        Path fileNamePath = Paths.get(dir.toString() + File.separator + fileName);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(fileNamePath)) {
            for (Path entry : stream) {
                Files.delete(entry);
            }
        } catch (IOException e) {
            e.getMessage();
        }
*/
    }

    @Override
    public void deleteAll(Path path) {
        try {
            FileUtils.deleteDirectory(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if(entry.toFile().isFile()) {
                    Files.delete(entry);
                }else {
                    deleteAll(entry);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        path.toFile().delete();*/
    }

    @Override
    public void archivingFile(String folder) {
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(dir.toString() + File.separator + folder + ".zip"));
            Path path = Paths.get(dir.toString() + File.separator + folder);
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path entry : stream) {
                if (entry.endsWith(folder + ".zip")) {
                    continue;
                }else {
                    FileInputStream fileInputStream = new FileInputStream(entry.toFile());
                    ZipEntry zipEntry = new ZipEntry(entry.toFile().getName());
                    zipOutputStream.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fileInputStream.read(bytes)) >= 0) {
                        zipOutputStream.write(bytes, 0, length);
                    }
                    fileInputStream.close();
                    //Files.delete(entry);
                }
            }
            //Files.delete(path);
            stream.close();
            zipOutputStream.close();
            FileUtils.deleteDirectory(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] download(String folder) {
        Path filePath = Paths.get(dir.toString() + File.separator + folder + ".zip");

        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
