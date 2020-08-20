package pl.maciejdudek.project.model.DTO;

import lombok.Getter;
import lombok.Setter;
import pl.maciejdudek.project.model.NoteStatus;

import java.time.OffsetDateTime;

@Getter @Setter
public class NoteDTO {
    private Long id;
    private String title;
    private String content;
    private OffsetDateTime createdAt;
    private NoteStatus noteStatus;
    private Long userId;
}
