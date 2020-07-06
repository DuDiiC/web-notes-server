package pl.maciejdudek.project.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NoteDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
}
