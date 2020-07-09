package pl.maciejdudek.project.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll(int page, int size, Sort.Direction sort, String by) {
        Page<Long> pagedIds = userRepository.findAllIds(PageRequest.of(page, size, Sort.by(sort, by)));
        if(pagedIds.hasContent()) {
            return userRepository.findAll(pagedIds.getContent());
        }
        return new ArrayList<>();
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
}
