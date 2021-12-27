package com.novmik.tpc.role;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoleController.class)
@ActiveProfiles("dev")
@WithMockUser(roles = "ADMIN")
class RoleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RoleService roleService;

  @Test
  void getAllRoles() throws Exception {
    mockMvc.perform(get("/api/v1/role")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void addNewRole() throws Exception {
    Role role = new Role(100L, "Test Role", Collections.emptyList());
    mockMvc.perform(post("/api/v1/role")
            .content(asJsonString(role))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void addPrivilegeToRole() throws Exception {
    AddPrivilegeToRoleRequest request = new AddPrivilegeToRoleRequest(
        "ROLE_TEST", "TEST");
    mockMvc.perform(post("/api/v1/role/addprivilege")
            .content(asJsonString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void deleteRoleById() throws Exception {
    mockMvc.perform(delete("/api/v1/role/{idRole}", 100L))
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