package com.novmik.tpc.privilege;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PrivilegeController.class)
@ActiveProfiles("dev")
@WithMockUser(roles = "ADMIN")
class PrivilegeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PrivilegeService privilegeService;

  @Test
  void getAllPrivilege() throws Exception {
    mockMvc.perform(get("/api/v1/privilege")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void addNewPrivilege() throws Exception {
    Privilege privilege = new Privilege(100L, "Test");
    mockMvc.perform(post("/api/v1/privilege")
        .content(asJsonString(privilege))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void deletePrivilegeById() throws Exception {
    mockMvc.perform(delete("/api/v1/privilege/{idPrivilege}", 100L))
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