package pl.maciejdudek.project.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.maciejdudek.project.configuration.Swagger2Config;
import pl.maciejdudek.project.model.DTO.LoginDTO;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@Api(tags = {Swagger2Config.LOGIN_AND_REGISTER_TAG})
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginDTO credentials) {
    }
}
