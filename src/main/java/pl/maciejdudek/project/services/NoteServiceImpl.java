package pl.maciejdudek.project.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.repositories.NoteRepository;
import pl.maciejdudek.project.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note getOne(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Note"));
    }

    @Override
    public Note save(Note note) {
        note.setCreatedAt(LocalDateTime.now());
        note.setUser(
                userRepository.findById(
                        note.getUser().getId())
                        .orElseThrow(() -> new ObjectNotFoundException(note.getUser().getId(), "User"))
        );
        return noteRepository.save(note);
    }

    @Override
    public Note update(Long id, Note note) {
        Note noteToUpdate = noteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Note"));
        noteToUpdate.setContent(note.getContent());
        return noteRepository.save(noteToUpdate);
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}