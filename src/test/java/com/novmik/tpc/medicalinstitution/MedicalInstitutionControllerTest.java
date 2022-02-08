package com.novmik.tpc.medicalinstitution;

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

@WebMvcTest(MedicalInstitutionController.class)
@ActiveProfiles("dev")
class MedicalInstitutionControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AuthenticationManager authenticationManagerBean;
  @MockBean
  private MedicalInstitutionService miService;

  @Test
  void getMedicalInstitutionList() throws Exception {
    mockMvc.perform(get("/api/v1/mi/{idSubject}/mil", 100L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getAllMedicalInstitutionsBySubjectId() throws Exception {
    mockMvc.perform(get("/api/v1/mi/{idSubject}/all", 100L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getMedicalInstitutionById() throws Exception {
    mockMvc.perform(get("/api/v1/mi/{idMi}", 150L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void addNewMedicalInstitution() throws Exception {
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        1L,
        "Test SubjectName",
        "Test MedicalInstitutionName",
        2F,
        "1",
        2F,
        "1",
        2F,
        0
    );
    mockMvc.perform(post("/api/v1/mi")
            .content(asJsonString(medicalInstitution))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void updateMedicalInstitution() throws Exception {
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        1L,
        "Test SubjectName",
        "Test MedicalInstitutionName",
        2F,
        "1",
        2F,
        "1",
        2F,
        0
    );
    mockMvc.perform(put("/api/v1/mi")
            .content(asJsonString(medicalInstitution))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @WithMockUser(authorities = "DELETE")
  @Test
  void deleteMedicalInstitutionById() throws Exception {
    mockMvc.perform(delete("/api/v1/mi/{idMedicalInstitution}", 200L))
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