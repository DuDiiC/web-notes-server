package pl.maciejdudek.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.services.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping(("/api/users"))
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @GetMapping("/name")
    public User getOneByName(@RequestParam("username") String username) {
        return userService.getOneByName(username);
    }
}
