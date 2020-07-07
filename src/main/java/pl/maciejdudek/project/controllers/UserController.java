package pl.maciejdudek.project.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.configuration.security.CheckPermissions;
import pl.maciejdudek.project.exceptions.UnauthorizedException;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.services.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;
    private final CheckPermissions checkPermissions;

    // only for admin
    @GetMapping("/users")
    public List<UserDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "ASC") Sort.Direction sort, @RequestParam(defaultValue = "id") String by,
                                @AuthenticationPrincipal Principal principal) {
        if(checkPermissions.isAdmin(principal.getName())) {
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
        if(checkPermissions.isAdmin(principal.getName()) || checkPermissions.matchingId(principal.getName(), id)) {
            return modelMapper.map(userService.getOne(id), UserDTO.class);
        }
        throw new UnauthorizedException();
    }

    // only for admin and user where username equals id from request
    @GetMapping("/users/name")
    public UserDTO getOneByName(@RequestParam("username") String username,
                                @AuthenticationPrincipal Principal principal) {
        if(checkPermissions.isAdmin(principal.getName()) || principal.getName().equals(username)) {
            return modelMapper.map(userService.getOneByName(username), UserDTO.class);
        }
        throw new UnauthorizedException();
    }
}
