package pl.maciejdudek.project.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private Long id;
    private String username;
    private long[] noteIds;
}
