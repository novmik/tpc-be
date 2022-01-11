package com.novmik.tpc.costscheme;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.medicament.Medicament;
import com.novmik.tpc.medicament.MedicamentPrice;
import com.novmik.tpc.medicament.MedicamentPriceWithPackages;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CostSchemePharmacotherapyResponseTest {

  List<Medicament> medicamentList;
  List<MedicamentPriceWithPackages> medicamentPriceWithPackagesList;
  CostSchemePharmacotherapyResponse underTest;

  @BeforeEach
  void setUp() {
    MedicamentPrice medicamentPrice = new MedicamentPrice(30L, "Inn", 100F, 1_000D);
    MedicamentPriceWithPackages medicamentPriceWithPackages = new MedicamentPriceWithPackages(
        medicamentPrice, 10);
    Medicament medicament = new Medicament("Inn", "number Code Scheme", 150F, 0F, 0F, "мг/кг", 30,
        BigDecimal.ONE);
    medicamentList = List.of(medicament);
    medicamentPriceWithPackagesList = List.of(medicamentPriceWithPackages);
    underTest = new CostSchemePharmacotherapyResponse(BigDecimal.valueOf(50_000), medicamentList,
        medicamentPriceWithPackagesList);
  }

  @Test
  void getCostScheme() {
    assertThat(underTest.getCostScheme()).isEqualTo("50000");
  }

  @Test
  void getMedicaments() {
    assertThat(underTest.getMedicaments()).isEqualTo(medicamentList);
  }

  @Test
  void getMedicamentsPrice() {
    assertThat(underTest.getMedicamentsPrice()).isEqualTo(medicamentPriceWithPackagesList);
  }

  @Test
  void setCostScheme() {
    underTest.setCostScheme(BigDecimal.ONE);
    assertThat(underTest.getCostScheme()).isEqualTo("1");
  }

  @Test
  void setMedicaments() {
    underTest.setMedicaments(Collections.emptyList());
    assertThat(underTest.getMedicaments()).isEmpty();
  }

  @Test
  void setMedicamentsPrice() {
    underTest.setMedicamentsPrice(Collections.emptyList());
    assertThat(underTest.getMedicamentsPrice()).isEmpty();
  }

  @Test
  void testEquals() {
    CostSchemePharmacotherapyResponse test8 = new CostSchemePharmacotherapyResponse(BigDecimal.valueOf(50_000), medicamentList,
        medicamentPriceWithPackagesList);
    assertThat(underTest.equals(test8)).isTrue();
  }

  @Test
  void canEqual() {
    CostSchemePharmacotherapyResponse test3 = underTest;
    assertThat(underTest.canEqual(test3)).isTrue();
  }

  @Test
  void testHashCode() {
    CostSchemePharmacotherapyResponse test18 = new CostSchemePharmacotherapyResponse(BigDecimal.valueOf(50_000), medicamentList,
        medicamentPriceWithPackagesList);
      assertThat(underTest.hashCode()).isEqualTo(test18.hashCode());
}

  @Test
  void testToString() {
      assertThat(underTest.toString()).isEqualTo("CostSchemePharmacotherapyResponse(costScheme=50000, medicaments=[Medicament(innMedicament=Inn, numberCodeScheme=number Code Scheme, dose=150.0, doseMin=0.0, doseMax=0.0, unitOfMeasurement=мг/кг, numberDaysDrug=30, requiredDose=1)], medicamentsPrice=[MedicamentPriceWithPackages(quantityPackage=10)])");
}
}