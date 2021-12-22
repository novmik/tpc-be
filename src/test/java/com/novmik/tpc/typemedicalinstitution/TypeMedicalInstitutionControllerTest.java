package com.novmik.tpc.typemedicalinstitution;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TypeMedicalInstitutionController.class)
@ActiveProfiles("dev")
class TypeMedicalInstitutionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TypeMedicalInstitutionService typeMiService;

  @Test
  void getAllTypeMedicalInstitution() throws Exception {
    mockMvc.perform(get("/api/v1/type_mo")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}