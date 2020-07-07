package pl.maciejdudek.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.maciejdudek.project.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // todo: fix non-optimal query (HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!)
    @Override
    @Query(value = "select distinct u from User u left join fetch u.notes",
            countQuery = "select count(u) from User u")
    Page<User> findAll(Pageable page);

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameIgnoringCase(String username);
}
