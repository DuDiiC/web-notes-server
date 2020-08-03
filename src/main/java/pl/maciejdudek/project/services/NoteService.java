package pl.maciejdudek.project.services;

import org.springframework.data.domain.Sort;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;

import java.util.List;

public interface NoteService {

    List<Note> getAll(int page, int size, Sort.Direction sort, String by);
    Note getOne(Long id);

    List<Note> getAllByUser(Long id, int page, int size, Sort.Direction sort, String by);
    List<Note> getAllByUserAndNoteStatus(Long id, NoteStatus noteStatus, int page, int size, Sort.Direction sort, String by);

    Note save(Note note, String username);

    Note update(Long id, Note note);
    Note updateStatus(Long id, NoteStatus noteStatus);

    void delete(Long id);
}
