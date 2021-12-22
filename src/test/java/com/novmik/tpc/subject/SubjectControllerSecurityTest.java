package com.novmik.tpc.subject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novmik.tpc.client.CustomUserDetailsService;
import com.novmik.tpc.security.JwtAccessDeniedHandler;
import com.novmik.tpc.security.JwtAuthenticationEntryPoint;
import com.novmik.tpc.security.JwtTokenProvider;
import com.novmik.tpc.security.JwtTokenValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SubjectController.class)
class SubjectControllerSecurityTest {

  @MockBean
  private SubjectService subjectService;
  @MockBean
  private CustomUserDetailsService cudService;
  @MockBean
  private JwtAuthenticationEntryPoint jwtEntryPoint;
  @MockBean
  private JwtTokenProvider jwtTokenProvider;
  @MockBean
  private JwtTokenValidator jwtTokenValidator;
  @MockBean
  private JwtAccessDeniedHandler accessDenied;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void getIdAndNameSubjectFromTable() throws Exception {
    mockMvc.perform(get("/api/v1/s")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getAllSubjects() throws Exception {
    mockMvc.perform(get("/api/v1/s/all")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getSubjectById() throws Exception {
    mockMvc.perform(get("/api/v1/s/{idSubject}", 100L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @WithMockUser(roles = "ADMIN")
  @Test
  void addNewSubject() throws Exception {
    Subject subject = new Subject("Test", 200D, 300D);
    mockMvc.perform(post("/api/v1/s")
            .content(asJsonString(subject))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void updateSubject() throws Exception {
    Subject subject = new Subject("Test", 200D, 300D);
    mockMvc.perform(put("/api/v1/s")
            .content(asJsonString(subject))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @WithMockUser(authorities = "DELETE")
  @Test
  void deleteSubjectById() throws Exception {
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