package com.example.demoUserService.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserServiceImpl implements UserService {

    private final Path dir;

    public UserServiceImpl(Path path){this.dir = path;}

    @Override
    public long getSizeFolder() {

        long sizeDir = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.csv")) {
            for (Path entry: stream){
                sizeDir += Files.size(entry);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return sizeDir;
    }

    @Override
    public void deleteFile(String fileName) {

        Path fileNamePath = Paths.get(dir.toString() + "/" + fileName + ".csv");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.csv")){
            for (Path entry: stream){
                if(entry.toString().equals(fileNamePath.toString())){
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
            for (Path entry: stream){
                Files.delete(entry);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public byte[] download(Path fileNamePath) {
        return new byte[0];
    }
}
