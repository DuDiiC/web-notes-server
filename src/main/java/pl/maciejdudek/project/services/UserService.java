package pl.maciejdudek.project.services;

import pl.maciejdudek.project.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getOne(Long id);
    User getOneByName(String username);

    User register(User newUser);
}
