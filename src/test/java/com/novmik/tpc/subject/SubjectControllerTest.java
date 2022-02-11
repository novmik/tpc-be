package com.novmik.tpc.subject;

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

@WebMvcTest(SubjectController.class)
@ActiveProfiles("dev")
class SubjectControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AuthenticationManager authenticationManagerBean;
  @MockBean
  private SubjectService subjectService;

  @Test
  void canGetIdAndNameSubjectFromTable() throws Exception {
    mockMvc.perform(get("/api/v1/s")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void canGetAllSubjects() throws Exception {
    mockMvc.perform(get("/api/v1/s/all")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void canGetSubjectById() throws Exception {
    mockMvc.perform(get("/api/v1/s/{idSubject}", 100L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(authorities = "WRITE")
  void canAddNewSubject() throws Exception {
    Subject subject = new Subject("Test", 200D, 300D);
    mockMvc.perform(post("/api/v1/s")
            .content(asJsonString(subject))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(authorities = "WRITE")
  void canUpdateSubject() throws Exception {
    Subject subject = new Subject("Test", 200D, 300D);
    mockMvc.perform(put("/api/v1/s")
            .content(asJsonString(subject))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(authorities = "DELETE")
  void canDeleteSubjectById() throws Exception {
    mockMvc.perform(delete("/api/v1/s/{idSubject}", 100L))
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