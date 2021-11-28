package com.novmik.tpc.cdt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CaseCdtServiceTest {

    @Mock
    private CaseCdtRepository caseCdtRepository;
    private CaseCdtService underTest;

    @BeforeEach
    void setUp() { underTest = new CaseCdtService(caseCdtRepository); }

    @Test
    void canFindByCaseCdt() {
        String nominationCaseCdt = "Test NominationCaseCdt";
        underTest.findByNominationCaseCdt(nominationCaseCdt);
        verify(caseCdtRepository).findByNominationCaseCdt(nominationCaseCdt);
    }

    @Test
    void canSaveCaseCDTP() {
        CaseCdt caseCdt = new CaseCdt(
                1,
                "Test NominationCaseCdt"
        );
        underTest.save(caseCdt);
        ArgumentCaptor<CaseCdt> caseCDTPArgumentCaptor = ArgumentCaptor.forClass(CaseCdt.class);
        verify(caseCdtRepository).save(caseCDTPArgumentCaptor.capture());
        CaseCdt capturedCaseCdtp = caseCDTPArgumentCaptor.getValue();
        assertThat(capturedCaseCdtp).isEqualTo(caseCdt);
    }

    @Test
    void canSaveNameCaseCDTP() {
    }
}