package hu.bme.edu.handmade;

import com.google.gson.Gson;
import hu.bme.edu.handmade.mappers.EventMapper;
import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.services.impl.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HandmadeApplicationTests {
	/*
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EventService eventService;

	@Test
	@WithUserDetails()
	void whenUserAccessOpenEndpoint_thenOk() throws Exception {
		mockMvc.perform(get("/events"))
				.andExpect(status().isOk());
	}
	@Test
	@WithUserDetails()
	void whenUserAccessRestrictedEndpoint_thenIsForbidden() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		mockMvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(eventDto)))
				.andExpect(status().isForbidden());
	}

 */

}
