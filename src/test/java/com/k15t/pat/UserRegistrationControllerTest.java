package com.k15t.pat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k15t.pat.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final String payload = "{\"name\":\"name\",\"password\":\"password\",\"email\":\"test@test.com\",\"address\":\"address\"}";
    private final String incorrectData = "{\"name\":\"name1\",\"password\":\"password\",\"email\":\"test@test.com\",\"address\":\"address\"}";
    private final String success = "{\"name\":\"juli\",\"password\":\"password\",\"email\":\"test@test.com\",\"address\":\"address\"}";
    private final String already_exists = "{\"name\":\"duplicate\",\"password\":\"password\",\"email\":\"test@test.com\",\"address\":\"address\"}";
    private final String already_exists_api = "{\"name\":\"api\",\"password\":\"password\",\"email\":\"test@test.com\",\"address\":\"address\"}";

    @Test
    public void testRegistrationFrontend() throws Exception {
        User user = mapper.readValue(success, User.class);
        mockMvc.perform(post("/rest/registration")
            .flashAttr("user", user))
            .andExpect(status().isOk())
            .andExpect(model().attribute("success", equalTo(true)))
            .andExpect(model().attribute("newUser", equalTo(user)));
    }

    @Test
    public void testRegistrationFrontendWithIncorrectData() throws Exception {
        User user = mapper.readValue(incorrectData, User.class);
        mockMvc.perform(post("/rest/registration")
            .flashAttr("user", user))
            .andExpect(status().isOk())
            .andExpect(model().attribute("success", equalTo(null)))
            .andExpect(model().attribute("newUser", equalTo(null)));
    }

    @Test
    public void testRegistrationApi() throws Exception {
        MvcResult response = mockMvc.perform(post("/api/registration")
            .content(payload)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();
        User userResponse = mapper.readValue(response.getResponse().getContentAsString(), User.class);
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
    }

    @Test
    public void testRegistrationApiWithIncorrectInputData() throws Exception {
        mockMvc.perform(post("/api/registration")
            .content(incorrectData)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    @Test
    public void whenNameAlreadyExists_thenUserIsNotCreated() throws Exception {
        User user_already_exists = mapper.readValue(already_exists, User.class);
        mockMvc.perform(post("/rest/registration")
            .flashAttr("user", user_already_exists))
            .andExpect(status().isOk())
            .andReturn();

        mockMvc.perform(post("/rest/registration")
            .flashAttr("user", user_already_exists))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    public void whenNameAlreadyExists_thenUserIsNotCreatedThroughApi() throws Exception {
        mockMvc.perform(post("/api/registration")
            .content(already_exists_api)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        mockMvc.perform(post("/api/registration")
            .content(already_exists_api)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andReturn();

    }
}
