package pl.maciejdudek.project.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "User"));
    }

    @Override
    public User getOneByName(String username) {
        return userRepository.findByUsernameIgnoringCase(username)
                .orElseThrow(() -> new ObjectNotFoundException(username, "User"));
    }

    @Override
    public User register(User newUser) {
        return null;
    }
}
