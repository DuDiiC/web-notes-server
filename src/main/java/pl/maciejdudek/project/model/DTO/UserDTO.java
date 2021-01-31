package pl.maciejdudek.project.model.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserDTO {
    @ApiModelProperty(notes = "user ID")
    private Long id;
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "user notes IDs")
    private List<Long> noteIds;
}
