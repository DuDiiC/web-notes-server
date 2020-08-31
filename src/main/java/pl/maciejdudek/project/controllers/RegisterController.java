package pl.maciejdudek.project.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.model.DTO.RegisterDTO;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.services.RegisterService;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequiredArgsConstructor
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
