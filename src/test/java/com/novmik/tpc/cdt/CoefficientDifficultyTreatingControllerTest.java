package com.novmik.tpc.cdt;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CoefficientDifficultyTreatingController.class)
@ActiveProfiles("dev")
class CoefficientDifficultyTreatingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CoefficientDifficultyTreatingService cdtService;

  @Test
  void getStCdtpListBySubjectId() throws Exception {
    mockMvc.perform(get("/api/v1/cdt/{idSubject}/st", 100L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getDsCdtpListBySubjectId() throws Exception {
    mockMvc.perform(get("/api/v1/cdt/{idSubject}/ds", 100L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void addNewCdtp() throws Exception {
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating();
    mockMvc.perform(post("/api/v1/cdt")
            .content(asJsonString(cdt))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}