package com.novmik.tpc.typemedicalinstitution;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TypeMedicalInstitutionServiceTest {

  @Mock
  private TypeMedicalInstitutionRepository typeMiRepository;
  private TypeMedicalInstitutionService underTest;

  @BeforeEach
  void setUp() {
    underTest = new TypeMedicalInstitutionService(typeMiRepository);
  }

  @Test
  void getAllTypeMedicalInstitution() {
    underTest.getAllTypeMedicalInstitution();
    verify(typeMiRepository).findAll();
  }
}