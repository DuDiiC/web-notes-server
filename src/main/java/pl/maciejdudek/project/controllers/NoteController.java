package pl.maciejdudek.project.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.model.DTO.NoteDTO;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;
import pl.maciejdudek.project.services.NoteServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {

    private final NoteServiceImpl noteService;
    private final ModelMapper modelMapper;

    // todo: add pagination
    // todo: add security (only for admin)
    @GetMapping("/notes")
    public List<NoteDTO> getAll() {
        return noteService.getAll().stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

    // todo: add security (only for admin and user who created note)
    @GetMapping("/notes/{id}")
    public NoteDTO getOne(@PathVariable Long id) {
        return modelMapper.map(
                noteService.getOne(id),
                NoteDTO.class);
    }

    // todo: add pagination
    // todo: add security (only for admin and user where userId equals id from request)
    @GetMapping("/users/{id}/notes")
    public List<NoteDTO> getAllByUser(@PathVariable Long id) {
        return noteService.getAllByUser(id).stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

    // todo: set default user id (user who creating note)
    @PostMapping("/notes")
    public NoteDTO save(@RequestBody NoteDTO noteDTO) {
        return modelMapper.map(
                noteService.save(modelMapper.map(noteDTO, Note.class)),
                NoteDTO.class);
    }

    // todo: add security (only for user who created note)
    @PutMapping("/notes/{id}")
    public NoteDTO update(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        return modelMapper.map(
                noteService.update(id, modelMapper.map(noteDTO, Note.class)),
                NoteDTO.class);
    }

    // todo: add security (only for user who created note)
    @PutMapping("/notes/{id}/status")
    public NoteDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, NoteStatus> statusJson) {
        return modelMapper.map(
                noteService.updateStatus(id, statusJson.get("status")),
                NoteDTO.class
        );
    }

    // todo: add security (only for admin)
    @DeleteMapping("/notes/{id}")
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }
}
