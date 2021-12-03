package com.novmik.tpc.diagnosis;

import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DiagnosisPriceService {

  private final DiagnosisPriceRepository diagnosisPriceRepository;
  private final SubjectService subjectService;

  protected List<DiagnosisPrice> getDiagnosisPriceList(final Long idSubject) {
    Subject subjectById = subjectService.getSubjectById(idSubject).orElseThrow();
    return diagnosisPriceRepository.findAllByNameSubject(subjectById.getNameSubject());
  }
}
