package pl.maciejdudek.project.services;

import pl.maciejdudek.project.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> getAll();
    Note getOne(Long id);

    Note save(Note note);

    Note update(Long id, Note note);

    void delete(Long id);
}
