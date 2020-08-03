package pl.maciejdudek.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findAll(Pageable page);
    Page<Note> findAllByUserId(Long id, Pageable page);
    Page<Note> findAllByUserIdAndNoteStatus(Long id, NoteStatus noteStatus, Pageable pageable);
}
