package com.novmik.tpc.schemepharmacotherapy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SchemePharmacotherapyController.class)
@ActiveProfiles("dev")
class SchemePharmacotherapyControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AuthenticationManager authenticationManagerBean;
  @MockBean
  private SchemePharmacotherapyService spService;

  @Test
  void canGetAllSchemes() throws Exception {
    mockMvc.perform(get("/api/v1/scheme")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getSchemePharmacotherapy() throws Exception {
    mockMvc.perform(get("/api/v1/scheme/{codeScheme}", "sh0001")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void canUpdateScheme() throws Exception {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy(
        "sh000", "МНН МП", "Описание СЛТ", 10, "st19.001", "ds19.001");
    mockMvc.perform(put("/api/v1/scheme")
        .content(asJsonString(scheme))
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