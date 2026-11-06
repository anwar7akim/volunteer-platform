package com.voluntier.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** End-to-end API checks that tie the running app to the test cases. */
@SpringBootTest
@AutoConfigureMockMvc
class ApiFlowTest {
    @Autowired MockMvc mvc;

    private String login(String email) throws Exception {
        String body = mvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"password\"}"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return body.replaceAll(".*\"token\":\"([^\"]+)\".*", "$1");
    }

    @Test void TC001_api_volunteerLoginReturnsRole() throws Exception {
        mvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"volunteer@voluntier.test\",\"password\":\"password\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.role").value("VOLUNTEER"));
    }

    @Test void TC004_api_wrongPasswordReturns400() throws Exception {
        mvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"volunteer@voluntier.test\",\"password\":\"bad\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test void TC008_api_browseNeedsLoginThenListsOpportunities() throws Exception {
        mvc.perform(get("/api/opportunities")).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/opportunities").header("X-Auth-Token", login("volunteer@voluntier.test")))
            .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(3));
    }

    @Test void TC009_api_searchNarrowsResults() throws Exception {
        mvc.perform(get("/api/opportunities").param("search", "park")
                .header("X-Auth-Token", login("volunteer@voluntier.test")))
            .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].title").value("Park Cleanup"));
    }

    @Test void TC013_TC014_api_registerThenDuplicateIsRejected() throws Exception {
        String token = login("volunteer2@voluntier.test");
        String list = mvc.perform(get("/api/opportunities").param("search", "Food").header("X-Auth-Token", token))
            .andReturn().getResponse().getContentAsString();
        String id = list.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        mvc.perform(post("/api/opportunities/" + id + "/register").header("X-Auth-Token", token))
            .andExpect(status().isOk());
        mvc.perform(post("/api/opportunities/" + id + "/register").header("X-Auth-Token", token))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error", containsString("already registered")));
    }

    @Test void TC006_api_volunteerCannotPostOpportunity() throws Exception {
        mvc.perform(post("/api/opportunities").contentType(MediaType.APPLICATION_JSON)
                .header("X-Auth-Token", login("volunteer@voluntier.test"))
                .content("{\"title\":\"x\",\"description\":\"d\",\"category\":\"c\",\"start\":\"2099-01-01T10:00:00\",\"location\":\"l\",\"capacity\":3}"))
            .andExpect(status().isBadRequest());
    }

    @Test void TC023_api_coordinatorPostsOpportunity() throws Exception {
        mvc.perform(post("/api/opportunities").contentType(MediaType.APPLICATION_JSON)
                .header("X-Auth-Token", login("coordinator@voluntier.test"))
                .content("{\"title\":\"Library Help\",\"description\":\"d\",\"category\":\"Education\",\"start\":\"2099-01-01T10:00:00\",\"location\":\"l\",\"capacity\":3}"))
            .andExpect(status().isCreated());
    }
}
