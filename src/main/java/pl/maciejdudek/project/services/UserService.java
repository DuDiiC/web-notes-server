package pl.maciejdudek.project.services;

import org.springframework.data.domain.Sort;
import pl.maciejdudek.project.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll(int page, int size, Sort.Direction sort, String by);
    User getOne(Long id);
    User getOneByName(String username);
}
