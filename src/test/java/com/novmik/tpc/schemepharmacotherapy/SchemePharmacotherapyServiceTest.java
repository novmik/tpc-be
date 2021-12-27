package com.novmik.tpc.schemepharmacotherapy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SchemePharmacotherapyServiceTest {

  @Mock
  private SchemePharmacotherapyRepository schemeRepository;
  private SchemePharmacotherapyService underTest;

  @BeforeEach
  void setUp() {
    underTest = new SchemePharmacotherapyService(schemeRepository);
  }

  @Test
  void canGetAllSchemePharmacotherapy() {
    underTest.getAllSchemes();
    verify(schemeRepository).findAll();
  }

  @Test
  void canFindByCodeScheme() {
    String codescheme = "sh0001";
    when(schemeRepository.findByCodeScheme(codescheme))
        .thenReturn(Optional.of(new SchemePharmacotherapy()));
    underTest.findByCodeScheme(codescheme);
    verify(schemeRepository).findByCodeScheme(codescheme);
  }

  @Test
  void willThrowWhenNotFindByCodeScheme() {
    String codeScheme = "sh0001";
    assertThatThrownBy(() -> underTest.findByCodeScheme(codeScheme))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Схема лекарственной терапии не найдена: " + codeScheme);
  }

  @Test
  void canUpdateScheme() {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy(
        90L, "sh000", "МНН МП", "Описание СЛТ", 10, "st19.001", "ds19.001");
    when(schemeRepository.existsById(scheme.getIdScheme())).thenReturn(true);
    underTest.updateScheme(scheme);
    verify(schemeRepository).save(scheme);
  }

  @Test
  void willThrowWhenUpdateSchemeIsNull() {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy();
    assertThatThrownBy(() -> underTest.updateScheme(scheme))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Некорректные данные о СЛТ: ");
  }

  @Test
  void willThrowWhenUpdateSchemeWhichNotExists() {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy(
        90L, "sh000", "МНН МП", "Описание СЛТ", 10, "st19.001", "ds19.001");
    when(schemeRepository.existsById(scheme.getIdScheme())).thenReturn(false);
    assertThatThrownBy(() -> underTest.updateScheme(scheme))
        .isInstanceOf(NotFoundException.class)
        .hasMessage(
            "СЛТ с таким id/именем/названием не существует: " + scheme.getIdScheme());
    verify(schemeRepository, never()).save(scheme);
  }
}