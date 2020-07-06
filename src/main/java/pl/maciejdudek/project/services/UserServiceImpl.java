package pl.maciejdudek.project.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.repositories.NoteRepository;
import pl.maciejdudek.project.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
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
