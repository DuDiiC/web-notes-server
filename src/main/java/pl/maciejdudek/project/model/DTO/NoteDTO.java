package pl.maciejdudek.project.model.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import pl.maciejdudek.project.model.NoteStatus;

import java.time.OffsetDateTime;

@Getter @Setter
public class NoteDTO {
    @ApiModelProperty(notes = "note ID in database")
    private Long id;
    @ApiModelProperty(notes = "note title")
    private String title;
    @ApiModelProperty(notes = "note content in markdown format")
    private String content;
    @ApiModelProperty(notes = "creation timestamp of note")
    private OffsetDateTime createdAt;
    @ApiModelProperty(notes = "the current status of the note")
    private NoteStatus noteStatus;
    @ApiModelProperty(notes = "user ID to which the note belongs")
    private Long userId;
}
