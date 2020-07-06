package pl.maciejdudek.project.controllers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.services.UserServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/api"))
public class UserController {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserServiceImpl userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public List<UserDTO> getAll() {
        return userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDTO getOne(@PathVariable Long id) {
        return modelMapper.map(userService.getOne(id), UserDTO.class);
    }

    @GetMapping("/users/name")
    public UserDTO getOneByName(@RequestParam("username") String username) {
        return modelMapper.map(userService.getOneByName(username), UserDTO.class);
    }
}
