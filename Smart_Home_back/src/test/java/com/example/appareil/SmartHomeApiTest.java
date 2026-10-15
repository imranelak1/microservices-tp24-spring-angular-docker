package com.example.appareil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SmartHomeApiTest {
  @Autowired MockMvc mvc;

  @Test
  void categoryAndDeviceAreStoredAndSwitched() throws Exception {
    String category =
        mvc.perform(
                post("/api/controller/categorie/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"label\":\"Salon\"}"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    long categoryId =
        new com.fasterxml.jackson.databind.ObjectMapper().readTree(category).get("id").asLong();
    String device =
        mvc.perform(
                post("/api/controller/appareil/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"label\":\"Lampe\",\"description\":\"Salon\",\"state\":false,\"categorie\":{\"id\":"
                            + categoryId
                            + "}}"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    long id = new com.fasterxml.jackson.databind.ObjectMapper().readTree(device).get("id").asLong();
    mvc.perform(get("/api/controller/appareil/update/state/true")).andExpect(status().isOk());
    mvc.perform(get("/api/controller/appareil/id/" + id))
        .andExpect(jsonPath("$.state").value(true));
    mvc.perform(delete("/api/controller/appareil/id/" + id)).andExpect(status().isOk());
  }
}
