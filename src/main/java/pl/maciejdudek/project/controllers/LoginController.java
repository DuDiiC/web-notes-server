package pl.maciejdudek.project.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.maciejdudek.project.model.DTO.LoginDTO;

@RestController
public class LoginController {

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public void login(@RequestBody LoginDTO credentials) {
    }
}
