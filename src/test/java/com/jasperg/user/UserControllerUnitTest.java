package com.jasperg.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasperg.user.model.User;
import com.jasperg.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenUser_whenGetAllUsers_thenReturnJsonUsers() throws Exception {

        User user1 = new User("Jasper", "Guldentops",
                "jg@gmail.com", "jg@gmail.com-0000");
        User user2 = new User("Andre", "Arboon",
                "aa@gmail.com", "aa@gmail.com-0000");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        given(userRepository.findAll()).willReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].firstName", is("Jasper")))
                .andExpect(jsonPath("$[0].lastName", is("Guldentops")))
                .andExpect(jsonPath("$[0].email", is("jg@gmail.com")))
                .andExpect(jsonPath("$[0].code", is("jg@gmail.com-0000")))

                .andExpect(jsonPath("$[1].firstName", is("Andre")))
                .andExpect(jsonPath("$[1].lastName", is("Arboon")))
                .andExpect(jsonPath("$[1].email", is("aa@gmail.com")))
                .andExpect(jsonPath("$[1].code", is("aa@gmail.com-0000")));
    }

    @Test
    public void givenUser_whenGetUsersByEmailContaining_thenReturnJsonUsers() throws Exception {

        User user1 = new User("Jasper", "Guldentops",
                "jg@gmail.com", "jg@gmail.com-0000");
        User user2 = new User("Andre", "Arboon",
                "aa@gmail.com", "aa@gmail.com-0000");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        given(userRepository.findUsersByEmailContaining(".com")).willReturn(users);

        mockMvc.perform(get("/users/email/{email}", ".com"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].firstName", is("Jasper")))
                .andExpect(jsonPath("$[0].lastName", is("Guldentops")))
                .andExpect(jsonPath("$[0].email", is("jg@gmail.com")))
                .andExpect(jsonPath("$[0].code", is("jg@gmail.com-0000")))

                .andExpect(jsonPath("$[1].firstName", is("Andre")))
                .andExpect(jsonPath("$[1].lastName", is("Arboon")))
                .andExpect(jsonPath("$[1].email", is("aa@gmail.com")))
                .andExpect(jsonPath("$[1].code", is("aa@gmail.com-0000")));
    }

    @Test
    public void givenUser_whenGetUsersByNameContaining_thenReturnJsonUsers() throws Exception {

        User user1 = new User("Andre", "Arboon",
                "aa@gmail.com", "aa@gmail.com-0000");
        User user2 = new User("Barry", "Bakker",
                "bb@gmail.be", "bb@gmail.be-0000");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        given(userRepository.findUsersByFirstNameContainingOrLastNameContaining("b", "b")).willReturn(users);

        mockMvc.perform(get("/users/name/{name}", "b"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].firstName", is("Andre")))
                .andExpect(jsonPath("$[0].lastName", is("Arboon")))
                .andExpect(jsonPath("$[0].email", is("aa@gmail.com")))
                .andExpect(jsonPath("$[0].code", is("aa@gmail.com-0000")))

                .andExpect(jsonPath("$[1].firstName", is("Barry")))
                .andExpect(jsonPath("$[1].lastName", is("Bakker")))
                .andExpect(jsonPath("$[1].email", is("bb@gmail.be")))
                .andExpect(jsonPath("$[1].code", is("bb@gmail.be-0000")));
    }

    @Test
    public void givenUser_whenGetUserByCode_thenReturnJsonUser() throws Exception {

        User user = new User("Jasper", "Guldentops", "jg@gmail.com", "jg@gmail.com-0000");

        given(userRepository.findUserByCode("jg@gmail.com-0000")).willReturn(user);

        mockMvc.perform(get("/users/code/{code}", "jg@gmail.com-0000"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.firstName", is("Jasper")))
                .andExpect(jsonPath("$.lastName", is("Guldentops")))
                .andExpect(jsonPath("$.email", is("jg@gmail.com")))
                .andExpect(jsonPath("$.code", is("jg@gmail.com-0000")));
    }

    @Test
    public void whenPostUser_thenReturnJsonUser() throws Exception {

        User newUser = new User("Dirk", "Dekkers", "dd@gmail.be", "dd@gmail.be-0000");

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.firstName", is("Dirk")))
                .andExpect(jsonPath("$.lastName", is("Dekkers")))
                .andExpect(jsonPath("$.email", is("dd@gmail.be")))
                .andExpect(jsonPath("$.code", is("dd@gmail.be-0000")));
    }

    @Test
    public void whenPutUser_thenReturnJsonUser() throws Exception {

        User user = new User("Barry", "Bakker", "bb@gmail.be", "bb@gmail.be-0000");

        given(userRepository.findUserByCode("bb@gmail.be-0000")).willReturn(user);

        User updateUser = new User("Barrie", "Bakkers",
                "barrieb@gmail.be", "bb@gmail.be-0000");

        mockMvc.perform(put("/users")
                .content(mapper.writeValueAsString(updateUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.firstName", is("Barrie")))
                .andExpect(jsonPath("$.lastName", is("Bakkers")))
                .andExpect(jsonPath("$.email", is("barrieb@gmail.be")))
                .andExpect(jsonPath("$.code", is("bb@gmail.be-0000")));
    }

    @Test
    public void givenUser_whenDeleteUser_thenStatusOk() throws Exception {

        User user = new User("Conny", "Cunters", "cc@gmail.be", "cc@gmail.be-0000");

        given(userRepository.findUserByCode("cc@gmail.be-0000")).willReturn(user);

        mockMvc.perform(delete("/users/{code}", "cc@gmail.be-0000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUser_whenDeleteUser_thenStatusNotFound() throws Exception {

        given(userRepository.findUserByCode("Not a real code")).willReturn(null);

        mockMvc.perform(delete("/users/{code}", "Not a real code")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
