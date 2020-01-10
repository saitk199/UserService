package com.example.demoUserService.Controller;

import com.example.demoUserService.Service.UserService;
import com.example.demoUserService.Service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
