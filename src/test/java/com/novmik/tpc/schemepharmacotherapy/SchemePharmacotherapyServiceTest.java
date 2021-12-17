package com.novmik.tpc.schemepharmacotherapy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.novmik.tpc.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
class SchemePharmacotherapyServiceTest {

  @Mock
  private SchemePharmacotherapyRepository spRepository;
  private SchemePharmacotherapyService underTest;

  @BeforeEach
  void setUp() {
    underTest = new SchemePharmacotherapyService(spRepository);
  }

  @Test
  void canFindByCodeScheme() {
    String codescheme = "sh0001";
    when(spRepository.findByCodeScheme(codescheme))
        .thenReturn(Optional.of(new SchemePharmacotherapy()));
    underTest.findByCodeScheme(codescheme);
    verify(spRepository).findByCodeScheme(codescheme);
  }

  @Test
  void willThrowWhenNotFindByCodeScheme() {
    String codeScheme = "sh0001";
    assertThatThrownBy(() -> underTest.findByCodeScheme(codeScheme))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Схема лекарственной терапии не найдена: " + codeScheme);
  }
}