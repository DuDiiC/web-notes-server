package pl.maciejdudek.project.configuration;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<Note>, List<Long>> noteSetToLongArrayConverter =
                ctx -> ctx.getSource()
                        .stream()
                        .map(Note::getId)
                        .collect(Collectors.toList());

        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(map -> map
                        .using(noteSetToLongArrayConverter)
                        .map(
                                User::getNotes,
                                UserDTO::setNoteIds
                        ));

        return modelMapper;
    }
}
