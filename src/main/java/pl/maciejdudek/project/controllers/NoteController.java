package pl.maciejdudek.project.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maciejdudek.project.configuration.Swagger2Config;
import pl.maciejdudek.project.configuration.security.SecurityPermissionChecker;
import pl.maciejdudek.project.exceptions.UnauthorizedException;
import pl.maciejdudek.project.model.DTO.NoteDTO;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;
import pl.maciejdudek.project.services.NoteService;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(tags = {Swagger2Config.NOTES_TAG})
public class NoteController {

    private final NoteService noteService;
    private final ModelMapper modelMapper;
    private final SecurityPermissionChecker securityPermissionChecker;

    // only for admin
    @GetMapping("/notes")
    @ApiOperation(value = "get all notes")
    public List<NoteDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "ASC") Sort.Direction sort, @RequestParam(defaultValue = "id") String by,
                                @ApiIgnore @AuthenticationPrincipal Principal principal) {
        if (securityPermissionChecker.userIsAdmin(principal.getName())) {
            return noteService.getAll(page, size, sort, by).stream()
                    .map(note -> modelMapper.map(note, NoteDTO.class))
                    .collect(Collectors.toList());
        }
        throw new UnauthorizedException();
    }

    // only for admin and user who created note
    @GetMapping("/notes/{id}")
    @ApiOperation(value = "get selected note by ID")
    @ApiImplicitParam(name = "id", value = "note ID")
    public NoteDTO getOne(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal Principal principal) {
        String requestUsername = principal.getName();
        if (securityPermissionChecker.userIsNoteOwner(requestUsername, id) || securityPermissionChecker.userIsAdmin(requestUsername)) {
            return modelMapper.map(
                    noteService.getOne(id),
                    NoteDTO.class);
        }
        throw new UnauthorizedException();
    }

    // only for admin and user where userId equals id from request
    @GetMapping("/users/{id}/notes")
    @ApiOperation(value = "get all notes for user selected by ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "user ID"),
            @ApiImplicitParam(name = "noteStatus", value = "find notes with selected status (return all if not selected)")
    })
    public List<NoteDTO> getAllByUser(@PathVariable Long id, @RequestParam(required = false) NoteStatus noteStatus,
                                      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
                                      @RequestParam(defaultValue = "ASC") Sort.Direction sort, @RequestParam(defaultValue = "id") String by,
                                      @ApiIgnore @AuthenticationPrincipal Principal principal) {
        String requestUsername = principal.getName();
        if (securityPermissionChecker.usernameCorrespondsId(requestUsername, id) || securityPermissionChecker.userIsAdmin(requestUsername)) {
            if (noteStatus == null) {
                return noteService.getAllByUser(id, page, size, sort, by).stream()
                        .map(note -> modelMapper.map(note, NoteDTO.class))
                        .collect(Collectors.toList());
            } else {
                return noteService.getAllByUserAndNoteStatus(id, noteStatus, page, size, sort, by).stream()
                        .map(note -> modelMapper.map(note, NoteDTO.class))
                        .collect(Collectors.toList());
            }
        }
        throw new UnauthorizedException();
    }

    @PostMapping("/notes")
    @ApiOperation(value = "create new note")
    public NoteDTO save(@RequestBody NoteDTO noteDTO, @ApiIgnore @AuthenticationPrincipal Principal principal) {
        return modelMapper.map(
                noteService.save(modelMapper.map(noteDTO, Note.class), principal.getName()),
                NoteDTO.class);
    }

    // only for user who created note
    @PutMapping("/notes/{id}")
    @ApiOperation("update note selected by ID")
    @ApiImplicitParam(name = "id", value = "note ID")
    public NoteDTO update(@PathVariable Long id, @RequestBody NoteDTO noteDTO,
                          @ApiIgnore @AuthenticationPrincipal Principal principal) {
        if (securityPermissionChecker.userIsNoteOwner(principal.getName(), id)) {
            return modelMapper.map(
                    noteService.update(id, modelMapper.map(noteDTO, Note.class)),
                    NoteDTO.class);
        }
        throw new UnauthorizedException();
    }

    // only for user who created note
    @PutMapping("/notes/{id}/status")
    @ApiOperation(value = "update note status selected by ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "note ID"),
            @ApiImplicitParam(name = "statusJson", value = "new note status")
    })
    public NoteDTO updateStatus(@PathVariable Long id, @RequestBody Map<String, NoteStatus> statusJson,
                                @ApiIgnore @AuthenticationPrincipal Principal principal) {
        if (securityPermissionChecker.userIsNoteOwner(principal.getName(), id)) {
            return modelMapper.map(
                    noteService.updateStatus(id, statusJson.get("noteStatus")),
                    NoteDTO.class
            );
        }
        throw new UnauthorizedException();
    }

    // only for admin and user who created note
    @DeleteMapping("/notes/{id}")
    @ApiOperation(value = "delete note selected by ID")
    @ApiImplicitParam(name = "id", value = "note ID")
    public void delete(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal Principal principal) {
        String requestUsername = principal.getName();
        if (securityPermissionChecker.userIsNoteOwner(requestUsername, id) || securityPermissionChecker.userIsAdmin(requestUsername)) {
            noteService.delete(id);
        } else {
            throw new UnauthorizedException();
        }
    }
}
