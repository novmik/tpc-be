package com.novmik.tpc.diagnosis;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiagnosisPriceServiceTest {

  @Mock
  private DiagnosisPriceRepository dpRepository;
  @Mock
  private SubjectService subjectService;
  private DiagnosisPriceService underTest;

  @BeforeEach
  void setUp() {
    underTest = new DiagnosisPriceService(dpRepository, subjectService);
  }

  @Test
  void getDiagnosisPriceList() {
    Subject subject = new Subject(100L, "Test SubjectName", 100D, 200D);
    long idSubject = subject.getIdSubject();
    when(subjectService.getSubjectById(idSubject)).thenReturn(subject);
    underTest.getDiagnosisPriceList(idSubject);
    verify(dpRepository).findAllByNameSubject(subject.getNameSubject());
  }
}