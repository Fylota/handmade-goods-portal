package hu.bme.edu.handmade.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.edu.handmade.web.dto.EventDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    void givenRequestOnPublicService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/events").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void givenRequestOnPrivateService_shouldFailWith401() throws Exception {
        EventDto dto = new EventDto();
        mvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.valueOf(asJsonString(dto))))
                    .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void givenRequestOnPrivateServiceByUser_shouldFailWith403() throws Exception {
        EventDto dto = new EventDto();
        mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenRequestOnPrivateServiceByAdmin_shouldSucceedWith200() throws Exception {
        EventDto dto = new EventDto();
        dto.setTitle("Test");
        mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
