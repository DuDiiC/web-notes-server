package pl.maciejdudek.project.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;
import pl.maciejdudek.project.repositories.NoteRepository;
import pl.maciejdudek.project.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public List<Note> getAll(int page, int size, Sort.Direction sort, String by) {
        Page<Note> pagedResult = noteRepository.findAll(PageRequest.of(page, size, Sort.by(sort, by)));
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
        return new ArrayList<>();
    }

    @Override
    public Note getOne(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Note"));
    }

    @Override
    public List<Note> getAllByUser(Long id, int page, int size, Sort.Direction sort, String by) {
        Page<Note> pagedResult = noteRepository.findAllByUserId(id, PageRequest.of(page, size, Sort.by(sort, by)));
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Note> getAllByUserAndNoteStatus(Long id, NoteStatus noteStatus, int page, int size, Sort.Direction sort, String by) {
        Page<Note> pagedResult = noteRepository.findAllByUserIdAndNoteStatus(id, noteStatus, PageRequest.of(page, size, Sort.by(sort, by)));
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
        return new ArrayList<>();
    }

    @Override
    public Note save(Note note, String username) {
        note.setCreatedAt(OffsetDateTime.now());
        note.setNoteStatus(NoteStatus.ACTIVE);
        note.setUser(
                userRepository.findByUsername(
                        username)
                        .orElseThrow(() -> new ObjectNotFoundException(username, "User"))
        );
        return noteRepository.save(note);
    }

    @Override
    @Transactional
    public Note update(Long id, Note note) {
        Note noteToUpdate = noteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Note"));
        noteToUpdate.setTitle(note.getTitle());
        noteToUpdate.setContent(note.getContent());
        return noteToUpdate;
    }

    @Override
    @Transactional
    public Note updateStatus(Long id, NoteStatus noteStatus) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Note"));
        note.setNoteStatus(noteStatus);
        return note;
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}
