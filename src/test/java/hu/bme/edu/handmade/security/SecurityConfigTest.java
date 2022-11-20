package hu.bme.edu.handmade.security;

import hu.bme.edu.handmade.controllers.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithAnonymousUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ProductController.class, SecurityConfig.class})
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="admin", password="adminPass", roles={"USER","ADMIN"})
    void whenAdminAccessUserEndpoint_thenOk() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin", password="adminPass", roles={"USER","ADMIN"})
    void whenAdminAccessAdminSecuredEndpoint_thenIsOk() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin", password="adminPass", roles={"USER","ADMIN"})
    void whenAdminAccessDeleteSecuredEndpoint_thenIsOk() throws Exception {
        mockMvc.perform(delete("/delete").content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void whenAnonymousAccessLogin_thenOk() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username="user", password="userPass")
    void whenUserAccessUserSecuredEndpoint_thenOk() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="user", password="userPass")
    void whenUserAccessAdminSecuredEndpoint_thenIsForbidden() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="user", password="userPass")
    void whenUserAccessDeleteSecuredEndpoint_thenIsForbidden() throws Exception {
        mockMvc.perform(delete("/delete"))
                .andExpect(status().isForbidden());
    }
}
