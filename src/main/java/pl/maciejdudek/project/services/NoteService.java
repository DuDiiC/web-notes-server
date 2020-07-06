package pl.maciejdudek.project.services;

import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;

import java.util.List;

public interface NoteService {

    List<Note> getAll();
    Note getOne(Long id);

    Note save(Note note);

    Note update(Long id, Note note);
    Note updateStatus(Long id, NoteStatus noteStatus);

    void delete(Long id);

    List<Note> getAllByUser(Long id);
}
