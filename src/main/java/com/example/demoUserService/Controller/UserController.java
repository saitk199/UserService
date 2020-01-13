package com.example.demoUserService.Controller;

import com.example.demoUserService.Service.UserService;
import com.example.demoUserService.Service.UserServiceImpl;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UserController {

    private final Path path = Paths.get("D:\\Projects\\demoUserService\\tmp");

    @GetMapping("/size")
    public long getSize(){
        UserService userService = new UserServiceImpl(path);
        return userService.getSizeFolder();
    }

    @DeleteMapping("/delete")
    public void delFile(@RequestParam(name = "file") String file){
        UserService userService = new UserServiceImpl(path);
        userService.deleteFile(file);
    }

    @DeleteMapping("/deleteall")
    public void delAll(){
        UserService userService = new UserServiceImpl(path);
        userService.deleteAll();
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadZip(){
        UserService userService = new UserServiceImpl(path);
        userService.archivingFile();
        byte[] file = userService.download();
        Path zipPath = Paths.get(path.toString() + File.separator + "archiveFiles.zip");
        ByteArrayResource resource = new ByteArrayResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + zipPath.getFileName().toString())
                .contentLength(file.length)
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }

}
