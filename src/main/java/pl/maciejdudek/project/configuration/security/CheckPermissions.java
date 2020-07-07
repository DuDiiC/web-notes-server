package pl.maciejdudek.project.configuration.security;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Component;
import pl.maciejdudek.project.repositories.UserRepository;

@Component
public class CheckPermissions {

    private final UserRepository userRepository;

    public CheckPermissions(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAdmin(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ObjectNotFoundException(username, "User")
        ).getRole().equals("ROLE_ADMIN");
    }

    public boolean matchingId(String username, long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(id, "User")
        ).getUsername().equals(username);
    }
}
