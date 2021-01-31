package pl.maciejdudek.project.model.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "user password")
    private String password;
}
