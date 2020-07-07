package pl.maciejdudek.project.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.services.RegisterServiceImpl;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterServiceImpl registerService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public UserDTO register(@RequestBody User user) {
        return modelMapper.map(registerService.register(user), UserDTO.class);
    }
}
