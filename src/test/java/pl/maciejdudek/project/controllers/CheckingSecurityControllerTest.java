package pl.maciejdudek.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import pl.maciejdudek.project.model.DTO.LoginDTO;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.repositories.UserRepository;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckingSecurityControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private ObjectMapper mapper;

    private static LoginDTO userLoginDto;

    @BeforeAll
    static void initLoginDto() {
        userLoginDto = new LoginDTO("user_name", "user_password");
    }

    @Test
    void shouldGetContentForAll() throws Exception {
        //given
        //when
        mockMvc.perform(get("/test/unsecured"))
        //then
                .andExpect(status().is(200))
                .andExpect(content().string("content"));
    }

    @Test
    void shouldUnauthorizedToStandardUserContentWithoutToken() throws Exception {
        //given
        //when
        mockMvc.perform(get("/test/securedForUserTest"))
        //then
                .andExpect(status().is(401));
    }

    @Test
    void shouldUnauthorizedToAdminContentWithoutToken() throws Exception {
        //given
        //when
        mockMvc.perform(get("/test/securedForAdminTest"))
        //then
                .andExpect(status().is(401));
    }

    @Test
    @Transactional
    void shouldForbiddenForStandardUserWhenAdminIsRequired() throws Exception {
        //given
        createTestUser("ROLE_USER");
        final String jwt = mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(userLoginDto)))
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
        //when
        mockMvc.perform(get("/test/securedForAdminTest")
                .header("Authorization", jwt))
        //then
                .andExpect(status().is(403));
    }

    @Test
    @Transactional
    void shouldGetContentForAdmin() throws Exception {
        //given
        createTestUser("ROLE_ADMIN");
        final String jwt = mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(userLoginDto)))
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
        //when
        mockMvc.perform(get("/test/securedForAdminTest")
                .header("Authorization", jwt))
        //then
                .andExpect(status().is(200))
                .andExpect(content().string("admin content"));
    }

    @Test
    @Transactional
    void shouldGetContentForStandardUser() throws Exception {
        //given
        createTestUser("ROLE_USER");
        final String jwt = mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(userLoginDto)))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
        //when
        mockMvc.perform(get("/test/securedForUserTest")
                .header("Authorization", jwt))
        //then
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("user content"));
    }

    private void createTestUser(String role) {
        User givenUser = new User();
        givenUser.setEmail("test_user@mail.com");
        givenUser.setUsername("user_name");
        givenUser.setPassword(passwordEncoder.encode("user_password"));
        givenUser.setEnabled(true);
        givenUser.setRole(role);
        userRepository.save(givenUser);
    }
}