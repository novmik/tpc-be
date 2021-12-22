package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicamentServiceTest {

  @Mock
  private SchemePharmacotherapyService schemeService;
  private MedicamentService underTest;

  @BeforeEach
  void setUp() {
    underTest = new MedicamentService(schemeService);
  }

  @Test
  void canGetMedicamentListBySchemePharmacotherapy() {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy(
        "sh0001",
        "Inn1 + Inn2",
        "НУЖНО_УДАЛИТЬ Inn1 25 мг/м² в/в в 1-й, 8-й дни + Inn2 6 мг/кг в 1-й день; цикл 21 день",
        1,
        "st19.000",
        "ds19.000");
    when(schemeService.findByCodeScheme(scheme.getCodeScheme()))
        .thenReturn(scheme);
    assertThat(underTest.getMedicamentListBySchemePharmacotherapy(
        scheme.getCodeScheme()).size()).isEqualTo(2);
    assertThat(underTest.getMedicamentListBySchemePharmacotherapy(
        scheme.getCodeScheme()).get(0).getDose()).isEqualTo(25);
    assertThat(underTest.getMedicamentListBySchemePharmacotherapy(
        scheme.getCodeScheme()).get(1).getDose()).isEqualTo(6);
    assertThat(underTest.getMedicamentListBySchemePharmacotherapy(
        scheme.getCodeScheme()).get(0).getNumberDaysDrug()).isEqualTo(2);
    assertThat(underTest.getMedicamentListBySchemePharmacotherapy(
        scheme.getCodeScheme()).get(1).getNumberDaysDrug()).isEqualTo(1);
  }
}