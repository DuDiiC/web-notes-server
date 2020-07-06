package pl.maciejdudek.project.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.model.DTO.NoteDTO;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.services.NoteServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteServiceImpl noteService;
    private final ModelMapper modelMapper;

    @Autowired
    public NoteController(NoteServiceImpl noteService, ModelMapper modelMapper) {
        this.noteService = noteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/notes")
    public List<NoteDTO> getAll() {
        return noteService.getAll().stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/notes/{id}")
    public NoteDTO getOne(@PathVariable Long id) {
        return modelMapper.map(
                noteService.getOne(id),
                NoteDTO.class);
    }

    @GetMapping("/users/{id}/notes")
    public List<NoteDTO> getAllByUser(@PathVariable Long id) {
        return noteService.getAllByUser(id).stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/notes")
    public NoteDTO save(@RequestBody NoteDTO noteDTO) {
        return modelMapper.map(
                noteService.save(modelMapper.map(noteDTO, Note.class)),
                NoteDTO.class);
    }

    @PutMapping("/notes/{id}")
    public NoteDTO update(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        return modelMapper.map(
                noteService.update(id, modelMapper.map(noteDTO, Note.class)),
                NoteDTO.class);
    }

    @DeleteMapping("/notes/{id}")
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }
}
