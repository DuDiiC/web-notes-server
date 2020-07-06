package pl.maciejdudek.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.maciejdudek.project.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Query("select distinct u from User u join fetch u.notes")
    List<User> findAll();

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameIgnoringCase(String username);
}
