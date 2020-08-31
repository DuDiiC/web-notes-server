package pl.maciejdudek.project.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.configuration.security.SecurityPermissionChecker;
import pl.maciejdudek.project.exceptions.UnauthorizedException;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SecurityPermissionChecker securityPermissionChecker;

    // only for admin
    @GetMapping("/users")
    public List<UserDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "ASC") Sort.Direction sort, @RequestParam(defaultValue = "id") String by,
                                @AuthenticationPrincipal Principal principal) {
        if(securityPermissionChecker.userIsAdmin(principal.getName())) {
            return userService.getAll(page, size, sort, by).stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        }
        throw new UnauthorizedException();
    }

    // only for admin and user where userId equals id from request
    @GetMapping("/users/{id}")
    public UserDTO getOne(@PathVariable Long id,
                          @AuthenticationPrincipal Principal principal) {
        String requestUsername = principal.getName();
        if(securityPermissionChecker.userIsAdmin(requestUsername) || securityPermissionChecker.usernameCorrespondsId(requestUsername, id)) {
            return modelMapper.map(userService.getOne(id), UserDTO.class);
        }
        throw new UnauthorizedException();
    }

    // only for admin and user where username equals id from request
    @GetMapping("/users/name")
    public UserDTO getOneByName(@RequestParam("username") String username,
                                @AuthenticationPrincipal Principal principal) {
        String requestUsername = principal.getName();
        if(securityPermissionChecker.userIsAdmin(requestUsername) || requestUsername.equals(username)) {
            return modelMapper.map(userService.getOneByName(username), UserDTO.class);
        }
        throw new UnauthorizedException();
    }
}
