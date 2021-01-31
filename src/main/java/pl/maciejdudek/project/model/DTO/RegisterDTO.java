package pl.maciejdudek.project.model.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterDTO {
    @ApiModelProperty(notes = "user email")
    private String email;
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "user password")
    private String password;
}
