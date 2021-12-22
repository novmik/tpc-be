package com.novmik.tpc.costscheme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.novmik.tpc.medicament.Medicament;
import com.novmik.tpc.medicament.MedicamentPrice;
import com.novmik.tpc.medicament.MedicamentPriceService;
import com.novmik.tpc.medicament.MedicamentService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CostSchemePharmacotherapyServiceTest {

  @Mock
  private CostSchemePharmacotherapyService underTest;
  @Mock
  private MedicamentService medicamentService;
  @Mock
  private MedicamentPriceService medPriceService;

  @BeforeEach
  void setUp() {
    underTest = new CostSchemePharmacotherapyService(medicamentService, medPriceService);
  }

  @Test
  void canGetCostSchemePharmacotherapyResponse() {
    Medicament medicament = new Medicament(
        "МНН Тест",
        "sh0000",
        75F,
        0F,
        0F,
        "мг/м²",
        1,
        null);
    MedicamentPrice medicamentPrice1 = new MedicamentPrice("МНН Тест", 400F, 10_000D);
    MedicamentPrice medicamentPrice2 = new MedicamentPrice("МНН Тест", 200F, 5_000D);
    CostSchemePharmacotherapyRequest costSchemeRequest = new CostSchemePharmacotherapyRequest(
        null, List.of(medicament), 1D, 70D, 1.81D
    );
    when(medPriceService.getMedicalPriceList(anyString()))
        .thenReturn(List.of(medicamentPrice1, medicamentPrice2));
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest).getCostScheme())
        .isEqualTo("5000.00");
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest).getMedicaments())
        .isNotNull();
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest).getMedicamentsPrice())
        .isNotNull();
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest)).isNotNull();
  }

  @Test
  void canGetCostSchemePharmacotherapyResponseWithCostSchemeEqualZero() {
    Medicament medicament = new Medicament(
        "МНН Тест",
        "sh0000",
        0F,
        0F,
        0F,
        "мг/м²",
        1,
        null);
    MedicamentPrice medicamentPrice1 = new MedicamentPrice("МНН Тест", 400F, 10_000D);
    MedicamentPrice medicamentPrice2 = new MedicamentPrice("МНН Тест", 200F, 5_000D);
    CostSchemePharmacotherapyRequest costSchemeRequest = new CostSchemePharmacotherapyRequest(
        null, List.of(medicament), 1D, 70D, 1.81D
    );
    when(medPriceService.getMedicalPriceList(anyString()))
        .thenReturn(List.of(medicamentPrice1, medicamentPrice2));
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest).getCostScheme())
        .isEqualTo("0.00");
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest).getMedicaments())
        .isNotNull();
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest).getMedicamentsPrice())
        .isNotNull();
    assertThat(underTest.getCostSchemePharmacotherapy(costSchemeRequest)).isNotNull();
  }
}