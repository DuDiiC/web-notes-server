package pl.maciejdudek.project.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.configuration.Swagger2Config;
import pl.maciejdudek.project.configuration.security.SecurityPermissionChecker;
import pl.maciejdudek.project.exceptions.UnauthorizedException;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.services.UserService;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
@Api(tags = {Swagger2Config.USERS_TAG})
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SecurityPermissionChecker securityPermissionChecker;

    // only for admin
    @GetMapping("/users")
    @ApiOperation(value = "get all users with paging")
    public List<UserDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "ASC") Sort.Direction sort, @RequestParam(defaultValue = "id") String by,
                                @ApiIgnore @AuthenticationPrincipal Principal principal) {
        if (securityPermissionChecker.userIsAdmin(principal.getName())) {
            return userService.getAll(page, size, sort, by).stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        }
        throw new UnauthorizedException();
    }

    // only for admin and user where userId equals id from request
    @GetMapping("/users/{id}")
    @ApiOperation(value = "get selected user by ID")
    @ApiImplicitParam(name = "id", value = "user ID", required = true, dataTypeClass = Long.class, paramType = "path")
    public UserDTO getOne(@PathVariable Long id,
                          @ApiIgnore @AuthenticationPrincipal Principal principal) {
        String requestUsername = principal.getName();
        if (securityPermissionChecker.usernameCorrespondsId(requestUsername, id) || securityPermissionChecker.userIsAdmin(requestUsername)) {
            return modelMapper.map(userService.getOne(id), UserDTO.class);
        }
        throw new UnauthorizedException();
    }

    // only for admin and user where username equals id from request
    @GetMapping("/users/name")
    @ApiOperation(value = "get user by selected username")
    @ApiImplicitParam(name = "username", value = "name of the searched user")
    public UserDTO getOneByName(@RequestParam("username") String username,
                                @ApiIgnore @AuthenticationPrincipal Principal principal) {
        String requestUsername = principal.getName();
        if (requestUsername.equals(username) || securityPermissionChecker.userIsAdmin(requestUsername)) {
            return modelMapper.map(userService.getOneByName(username), UserDTO.class);
        }
        throw new UnauthorizedException();
    }
}
