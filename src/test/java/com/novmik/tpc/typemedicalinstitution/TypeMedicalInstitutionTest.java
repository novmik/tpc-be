package com.novmik.tpc.typemedicalinstitution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeMedicalInstitutionTest {

  TypeMedicalInstitution underTest;

  @BeforeEach
  void setUp() {
    underTest = new TypeMedicalInstitution(0, "Description Test");
  }

  @Test
  void getTypeMi() {
    assertThat(underTest.getTypeMi()).isEqualTo(0);
  }

  @Test
  void getDescription() {
    assertThat(underTest.getDescription()).isEqualTo("Description Test1");
  }

  @Test
  void setTypeMi() {
    underTest.setTypeMi(1);
    assertThat(underTest.getTypeMi()).isEqualTo(1);
  }

  @Test
  void setDescription() {
    underTest.setDescription("Set Test");
    assertThat(underTest.getDescription()).isEqualTo("Set Test");
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo("TypeMedicalInstitution(typeMi=0, description=Description Test)");
  }

  @Test
  void testEmptyCtor() {
    TypeMedicalInstitution test9 = new TypeMedicalInstitution();
    assertThat(test9).hasAllNullFieldsOrProperties();
  }
}