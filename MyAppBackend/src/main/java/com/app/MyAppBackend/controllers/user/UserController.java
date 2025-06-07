package com.app.MyAppBackend.controllers.user;

import com.app.MyAppBackend.model.entities.User;
import com.app.MyAppBackend.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    private User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
