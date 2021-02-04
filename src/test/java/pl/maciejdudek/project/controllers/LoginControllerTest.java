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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

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
    @Transactional
    void shouldReturnCorrectStatusAfterLogin() throws Exception {
        //given
        createTestUser();
        //when
        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(userLoginDto)))
                .andDo(print())
        //then
                .andExpect(status().is(200));
    }

    @Test
    @Transactional
    void shouldGiveCorrectTokenAfterLoginAsExistingUser() throws Exception {
        //given
        createTestUser();
        //when
        final String jwt = mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(userLoginDto)))
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
        //then
        assertNotNull(jwt);
        assertTrue(jwt.startsWith("Bearer"));
    }

    private void createTestUser() {
        User givenUser = new User();
        givenUser.setEmail("test_user@mail.com");
        givenUser.setUsername("user_name");
        givenUser.setPassword(passwordEncoder.encode("user_password"));
        givenUser.setEnabled(true);
        givenUser.setRole("ROLE_USER");
        userRepository.save(givenUser);
    }
}