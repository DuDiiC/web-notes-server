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
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteServiceImpl noteService;
    private final ModelMapper modelMapper;

    @Autowired
    public NoteController(NoteServiceImpl noteService, ModelMapper modelMapper) {
        this.noteService = noteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<NoteDTO> getAll() {
        return noteService.getAll().stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDTO getOne(@PathVariable Long id) {
        return modelMapper.map(
                noteService.getOne(id),
                NoteDTO.class);
    }

    @PostMapping
    public NoteDTO save(@RequestBody NoteDTO noteDTO) {
        return modelMapper.map(
                noteService.save(modelMapper.map(noteDTO, Note.class)),
                NoteDTO.class);
    }

    @PutMapping("/{id}")
    public NoteDTO update(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        return modelMapper.map(
                noteService.update(id, modelMapper.map(noteDTO, Note.class)),
                NoteDTO.class);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }
}
