package pl.maciejdudek.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.maciejdudek.project.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.id from User u")
    Page<Long> findAllIds(Pageable page);

    @Query("select distinct u from User u left join fetch u.notes where u.id IN (:ids)")
    List<User> findAll(@Param("ids") List<Long> ids);

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameIgnoringCase(String username);
}
