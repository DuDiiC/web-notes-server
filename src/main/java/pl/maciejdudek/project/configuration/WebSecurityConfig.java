package pl.maciejdudek.project.configuration;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.maciejdudek.project.model.DTO.UserDTO;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.services.UserDetailsServiceImpl;

import java.util.Set;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<Note>, long[]> noteSetToLongArrayConverter =
                ctx -> ctx.getSource()
                        .stream()
                        .mapToLong(Note::getId)
                        .toArray();

        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(map -> map
                    .using(noteSetToLongArrayConverter)
                    .map(
                            User::getNotes,
                            UserDTO::setNoteIds
                    ));

        return modelMapper;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                .and()
                .authorizeRequests()
//                .antMatchers("/api/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/*").permitAll()
                .and()
                .formLogin();
    }
}
