package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicamentPriceTest {

  MedicamentPrice underTest;

  @BeforeEach
  void setUp() {
    underTest = new MedicamentPrice(90L, "Inn test", 440F, 10_000D);
  }

  @Test
  void getIdMp() {
    assertThat(underTest.getIdMp()).isEqualTo(90L);
  }

  @Test
  void getInn() {
    assertThat(underTest.getInn()).isEqualTo("Inn test");
  }

  @Test
  void getDosage() {
    assertThat(underTest.getDosage()).isEqualTo(440F);
  }

  @Test
  void getPriceWithVat() {
    assertThat(underTest.getPriceWithVat()).isEqualTo(10_000D);
  }

  @Test
  void setIdMp() {
    underTest.setIdMp(9000L);
    assertThat(underTest.getIdMp()).isEqualTo(9000L);
  }

  @Test
  void setInn() {
    underTest.setInn("Set Test");
    assertThat(underTest.getInn()).isEqualTo("Set Test");
  }

  @Test
  void setDosage() {
    underTest.setDosage(1000F);
    assertThat(underTest.getDosage()).isEqualTo(1000F);
  }

  @Test
  void setPriceWithVat() {
    underTest.setPriceWithVat(1000_000D);
    assertThat(underTest.getPriceWithVat()).isEqualTo(1000_000D);
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo(
        "MedicamentPrice(idMp=90, inn=Inn test, dosage=440.0, priceWithVat=10000.0)");
  }
}