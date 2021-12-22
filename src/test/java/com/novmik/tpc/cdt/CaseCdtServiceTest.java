package com.novmik.tpc.cdt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CaseCdtServiceTest {

  @Mock
  private CaseCdtRepository caseCdtRepository;
  private CaseCdtService underTest;

  @BeforeEach
  void setUp() {
    underTest = new CaseCdtService(caseCdtRepository);
  }

  @Test
  void canFindByCaseCdt() {
    String nominationCaseCdt = "Test NominationCaseCdt";
    underTest.findByNominationCaseCdt(nominationCaseCdt);
    verify(caseCdtRepository).findByNominationCaseCdt(nominationCaseCdt);
  }

  @Test
  void canSaveCaseCdt() {
    CaseCdt caseCdt = new CaseCdt(
        1,
        "Test NominationCaseCdt"
    );
    underTest.save(caseCdt);
    ArgumentCaptor<CaseCdt> caseCdtArgumentCaptor = ArgumentCaptor.forClass(CaseCdt.class);
    verify(caseCdtRepository).save(caseCdtArgumentCaptor.capture());
    CaseCdt capturedCaseCdtp = caseCdtArgumentCaptor.getValue();
    assertThat(capturedCaseCdtp).isEqualTo(caseCdt);
  }

  @Test
  void canSaveNameCaseCdt() {
    String nominationCaseCdt = "Test nomination Case Cdt";
    underTest.save(nominationCaseCdt);
    verify(caseCdtRepository).findByNominationCaseCdt(nominationCaseCdt);
  }
}