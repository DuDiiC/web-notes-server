package pl.maciejdudek.project.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.configuration.Swagger2Config;
import pl.maciejdudek.project.model.DTO.RegisterDTO;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.services.RegisterService;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequiredArgsConstructor
@Api(tags = {Swagger2Config.LOGIN_AND_REGISTER_TAG})
public class RegisterController {

    private final RegisterService registerService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterDTO user) {
        return modelMapper.map(
                registerService.register(modelMapper.map(user, User.class)),
                UserDTO.class);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        return registerService.confirmRegistration(token);
    }
}
