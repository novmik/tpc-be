package com.novmik.tpc.costtreatment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CostOfCompletedCaseOfTreatmentController.class)
@ActiveProfiles("dev")
class CostOfCompletedCaseOfTreatmentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CostOfCompletedCaseOfTreatmentService ctService;

  @Test
  void getCostTreatment() throws Exception {
    CostTreatmentRequest ctRequest = new CostTreatmentRequest();
    mockMvc.perform(post("/api/v1/costtreatment")
            .content(asJsonString(ctRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
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