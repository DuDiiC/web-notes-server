package pl.maciejdudek.project.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.services.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    // todo: add pagination
    // todo: add security (only for admin)
    @GetMapping("/users")
    public List<UserDTO> getAll() {
        return userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    // todo: add security (only for admin and user where userId equals id from request)
    @GetMapping("/users/{id}")
    public UserDTO getOne(@PathVariable Long id) {
        return modelMapper.map(userService.getOne(id), UserDTO.class);
    }

    // todo: add security (only for admin and user where userId equals id from request)
    @GetMapping("/users/name")
    public UserDTO getOneByName(@RequestParam("username") String username) {
        return modelMapper.map(userService.getOneByName(username), UserDTO.class);
    }
}
