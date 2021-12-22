package com.novmik.tpc.client;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novmik.tpc.auth.RegistrationRequest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClientController.class)
@ActiveProfiles("dev")
@WithMockUser(roles = "ADMIN")
class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  @Test
  void getClients() throws Exception {
    mockMvc.perform(get("/api/v1/client/list")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void registerUser() throws Exception {
    RegistrationRequest regRequest = new RegistrationRequest();
    Client newClient = new Client("test@test.com", "pass", "T", "E", true, true);
    when(clientService.registerClient(regRequest)).thenReturn(Optional.of(newClient));
    mockMvc.perform(post("/api/v1/client/register")
            .content(asJsonString(regRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void addRoleToClient() throws Exception {
    AddRoleToClientRequest addRoleRequest = new AddRoleToClientRequest();
    mockMvc.perform(post("/api/v1/client/addrole")
            .content(asJsonString(addRoleRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void deleteClient() throws Exception {
    mockMvc.perform(delete("/api/v1/client/{idClient}", 100L))
        .andExpect(status().isOk());
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}