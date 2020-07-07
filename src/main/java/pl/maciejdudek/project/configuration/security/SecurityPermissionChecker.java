package pl.maciejdudek.project.configuration.security;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Component;
import pl.maciejdudek.project.repositories.NoteRepository;
import pl.maciejdudek.project.repositories.UserRepository;

@Component
public class SecurityPermissionChecker {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public SecurityPermissionChecker(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    public boolean userIsAdmin(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ObjectNotFoundException(username, "User")
        ).getRole().equals("ROLE_ADMIN");
    }

    public boolean usernameCorrespondsId(String username, long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(id, "User")
        ).getUsername().equals(username);
    }

    public boolean userIsOwnerOfNote(String username, long noteId) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ObjectNotFoundException(username, "User")
        ).getId().equals(noteRepository.findById(noteId).orElseThrow(
                () -> new ObjectNotFoundException(noteId, "Note")
        ).getUser().getId());
    }
}
