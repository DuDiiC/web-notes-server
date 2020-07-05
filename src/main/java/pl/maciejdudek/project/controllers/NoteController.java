package pl.maciejdudek.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.services.NoteServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteServiceImpl noteService;

    @Autowired
    public NoteController(NoteServiceImpl noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAll() {
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    public Note getOne(@PathVariable Long id) {
        return noteService.getOne(id);
    }

    @PostMapping
    public Note save(@RequestBody Note note) {
        return noteService.save(note);
    }

    @PutMapping("/{id}")
    public Note update(@PathVariable Long id, @RequestBody Note note) {
        return noteService.update(id, note);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }
}
