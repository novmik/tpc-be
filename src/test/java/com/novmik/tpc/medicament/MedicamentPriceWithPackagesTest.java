package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MedicamentPriceWithPackagesTest {

  MedicamentPrice medicamentPrice;
  MedicamentPriceWithPackages underTest;

  @BeforeEach
  void setUp() {
    medicamentPrice = new MedicamentPrice(80L, "Inn", 250F, 5_000D);
    underTest = new MedicamentPriceWithPackages(medicamentPrice, 5);
  }

  @Test
  void getQuantityPackage() {
    assertThat(underTest.getQuantityPackage()).isEqualTo(5);
  }

  @Test
  void setQuantityPackage() {
    underTest.setQuantityPackage(10);
    assertThat(underTest.getQuantityPackage()).isEqualTo(10);
  }

  @Test
  void testEquals() {
    MedicamentPriceWithPackages test1 = new MedicamentPriceWithPackages(medicamentPrice, 5);
    assertThat(underTest.equals(test1)).isTrue();
  }

  @Test
  void canEqual() {
    MedicamentPriceWithPackages test5 = underTest;
    assertThat(underTest.canEqual(test5)).isTrue();
  }

  @Test
  void testHashCode() {
    MedicamentPriceWithPackages test11 = new MedicamentPriceWithPackages(medicamentPrice, 5);
    assertThat(underTest.hashCode()).isEqualTo(test11.hashCode());
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo("MedicamentPriceWithPackages(quantityPackage=5)");
  }

  @Test
  void fromMedicamentPriceToWithQuantityPackages() {
    assertThat(MedicamentPriceWithPackages.fromMedicamentPriceToWithQuantityPackages(List.of(medicamentPrice), 4).get(0).getQuantityPackage()).isEqualTo(4);
    assertThat(MedicamentPriceWithPackages.fromMedicamentPriceToWithQuantityPackages(List.of(medicamentPrice), 4)).isNotNull();
  }
}