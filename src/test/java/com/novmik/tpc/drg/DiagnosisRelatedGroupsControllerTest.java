package com.novmik.tpc.drg;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DiagnosisRelatedGroupsController.class)
@ActiveProfiles("dev")
class DiagnosisRelatedGroupsControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AuthenticationManager authenticationManagerBean;
  @MockBean
  private DiagnosisRelatedGroupsService drgService;

  @Test
  void canGetDiagnosisRelatedGroups() throws Exception {
    mockMvc.perform(get("/api/v1/drg/{drg}", "st19.062")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void canGetAllDrgs() throws Exception {
    mockMvc.perform(get("/api/v1/drg")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(authorities = "WRITE")
  void canAddNewDrg() throws Exception {
    DiagnosisRelatedGroups drg = new DiagnosisRelatedGroups(
        "st19.062", "Test", 10F, 20F);
    mockMvc.perform(post("/api/v1/drg")
            .content(asJsonString(drg))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(authorities = "WRITE")
  void canUpdateDrg() throws Exception {
    DiagnosisRelatedGroups drg = new DiagnosisRelatedGroups(
        300L, "st19.062", "Test", 10F, 20F);
    mockMvc.perform(put("/api/v1/drg")
            .content(asJsonString(drg))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(authorities = "DELETE")
  void canDeleteDrgById() throws Exception {
    mockMvc.perform(delete("/api/v1/drg/{idDrg}", 100L))
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