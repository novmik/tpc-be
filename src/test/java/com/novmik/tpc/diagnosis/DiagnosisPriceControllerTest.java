package com.novmik.tpc.diagnosis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DiagnosisPriceController.class)
@ActiveProfiles("dev")
class DiagnosisPriceControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AuthenticationManager authenticationManagerBean;
  @MockBean
  private DiagnosisPriceService dpService;

  @Test
  void getDiagnosisPriceList() throws Exception {
    mockMvc.perform(get("/api/v1/dp/{idSubject}", 100L)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}