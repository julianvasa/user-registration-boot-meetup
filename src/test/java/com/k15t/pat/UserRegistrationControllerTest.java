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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void testRegistrationFrontend() throws Exception {
        User user = mapper.readValue(payload, User.class);
        mockMvc.perform(post("/rest/registration")
            .flashAttr("user", user))
            .andExpect(status().isOk())
            .andExpect(model().attribute("success", equalTo("NO_ERRORS")))
            .andExpect(model().attribute("newUser", equalTo(user)));
    }

    @Test
    public void testRegistrationApi() throws Exception {
        User user = mapper.readValue(payload, User.class);
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
}
