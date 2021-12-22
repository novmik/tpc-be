package com.novmik.tpc.costscheme;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CostSchemePharmacotherapyController.class)
@ActiveProfiles("dev")
class CostSchemePharmacotherapyControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CostSchemePharmacotherapyService costSchemeService;

  @Test
  void getCostSchemePharmacotherapyByCodeScheme() throws Exception {
    CostSchemePharmacotherapyRequest costSchemeRequest = new CostSchemePharmacotherapyRequest(
        "sh0001", new ArrayList<>(), 1D, 70D, 1.8D
    );
    mockMvc.perform(post("/api/v1/costscheme")
            .content(asJsonString(costSchemeRequest))
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