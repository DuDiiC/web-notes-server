package pl.maciejdudek.project.model.DTO;

import lombok.Getter;
import lombok.Setter;
import pl.maciejdudek.project.model.NoteStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class NoteDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private NoteStatus noteStatus;
    private Long userId;
}
