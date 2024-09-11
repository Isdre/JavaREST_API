package com.example.springapi;

import com.example.springapi.api.controller.UserController;
import com.example.springapi.api.model.User;
import com.example.springapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SpringApiApplicationControllerTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUsers() throws Exception {
        User user1 = new User("John", LocalDate.of(1990, 1, 1));
        User user2 = new User("Jane", LocalDate.of(1985, 1, 1));

        when(userService.getUsers()).thenReturn(Arrays.asList(user1, user2));

        var x = mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(x);
    }

    @Test
    void getUser() throws Exception {
        User user = new User("John", LocalDate.of(1990, 1, 1));
        when(userService.getUser(1L)).thenReturn(Optional.of(user));

        var x = mockMvc.perform(get("/api/user?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John")).andReturn().getResponse().getContentAsString();
        System.out.println(x);
    }

    @Test
    void createUser() throws Exception {
        String json = "{\"name\":\"John\", \"dob\":\"1990-01-01\"}";

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()).andDo(result -> {
                    System.out.println("Create User Response");
                })
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        String json = "{\"name\":\"John\", \"dob\":\"1990-01-01\"}";

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()).andDo(result -> {
                    System.out.println("Create User Response");
                })
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isOk()).andDo(result -> {
                    System.out.println("Deleted User");
                });
    }

    @Test
    void updateUser() throws Exception {
        String json = "{\"name\":\"John\", \"dob\":\"1980-01-01\"}";

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()).andDo(result -> {
                    System.out.println("Create User Response");
                })
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/user/1")
                        .param("name", "John Updated")
                        .param("dob", "1990-01-01"))
                .andExpect(status().isOk()).andDo(result -> {
                    System.out.println("Updated User Response");
                })
                .andExpect(status().isOk());
    }
}
