package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.mocks.UserMocks;
import br.com.caelum.carangobom.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldFetchAllUsers() throws Exception {
        given(userService.findAllUsers()).willReturn(UserMocks.getListUsers());

        mockMvc.perform(get("/carangobom/v1/user"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFetchOneUserById() throws Exception {
        final Long userId = 1L;
        User gabriel = UserMocks.getGabriel();
        given(userService.findUserById(userId)).willReturn(gabriel);

        mockMvc.perform(get("/carangobom/v1/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(gabriel.getUsername())));
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        given(userService.createUser(any())).willReturn(UserMocks.getArthur());

        mockMvc.perform(post("/carangobom/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"gabriel\",\n" +
                        "    \"password\": \"teste123\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        final Long userId = 4L;
        given(userService.findUserById(any())).willReturn(UserMocks.getGabriel());
        doNothing().when(userService).deleteUser(any());

        mockMvc.perform(delete("/carangobom/v1/user/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdatePassword() throws Exception {
        final Long userId = 1L;
        given(userService.findUserById(any())).willReturn(UserMocks.getGabriel());

        mockMvc.perform(patch("/carangobom/v1/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"123\"}"))
                .andExpect(status().isOk());
    }

}
