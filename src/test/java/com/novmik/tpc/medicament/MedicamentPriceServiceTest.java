package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.novmik.tpc.exception.NotFoundException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicamentPriceServiceTest {

  @Mock
  private MedicamentPriceRepository mpRepository;
  private MedicamentPriceService underTest;

  @BeforeEach
  void setUp() {
    underTest = new MedicamentPriceService(mpRepository);
  }

  @Test
  void canGetMedicalPriceList() {
    MedicamentPrice medicamentPrice = new MedicamentPrice("Inn Test", 400F, 10_000D);
    when(mpRepository.findByInn(medicamentPrice.getInn())).thenReturn(List.of(medicamentPrice));
    assertThat(underTest.getMedicalPriceList(medicamentPrice.getInn())).isNotNull();

  }

  @Test
  void willThrowWhenGetMedicalPriceList() {
    String inn = "Inn Test";
    when(mpRepository.findByInn(anyString())).thenReturn(Collections.emptyList());
    assertThatThrownBy(() -> underTest.getMedicalPriceList(inn))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Список лекарств не найден, МНН: " + inn);
  }
}