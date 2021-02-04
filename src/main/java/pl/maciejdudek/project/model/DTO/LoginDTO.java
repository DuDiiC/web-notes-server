package pl.maciejdudek.project.model.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "user password")
    private String password;
}
