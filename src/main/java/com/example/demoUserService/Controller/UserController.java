package com.example.demoUserService.Controller;

import com.example.demoUserService.Service.UserService;
import com.example.demoUserService.Service.UserServiceImpl;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class UserController {

    private final Path path = Paths.get("D:\\Projects\\demoUserService\\tmp");

    private UserService userService = new UserServiceImpl(path);

    @GetMapping("/size")
    public long getSize(){
        return userService.getSizeFolder();
    }

    @DeleteMapping("/delete")
    public void delFile(@RequestParam(name = "file") String file){
        userService.deleteFile(file);
    }

    @DeleteMapping("/deleteall")
    public void delAll(){
        userService.deleteAll();
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadZip(@RequestParam(name = "folder") String folder){

        userService.zipFolder(folder);
        Path zipPath = Paths.get(path.toString() + File.separator + folder + ".zip");
        if (zipPath.toFile().exists()) {
            byte[] file = userService.zipToByte(zipPath);
            ByteArrayResource resource = new ByteArrayResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + zipPath.getFileName().toString())
                    .contentLength(file.length)
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(resource);
        }else{
            return new ResponseEntity (
                    "This file not found",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/code")
    public List<String> getCode(){
        return userService.getCodeByZipNames();
    }

    @GetMapping("/code-by-folder")
    public List<String> getCodeByFolder(){
        return userService.getCodeByFolder();
    }
}
