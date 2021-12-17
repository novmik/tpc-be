package com.novmik.tpc.schemepharmacotherapy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SchemePharmacotherapyController.class)
@ActiveProfiles("dev")
class SchemePharmacotherapyControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SchemePharmacotherapyService spService;

  @Test
  void getSchemePharmacotherapy() throws Exception {
    mockMvc.perform(get("/api/v1/codescheme/{codeScheme}", "sh0001"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}