package com.novmik.tpc.diagnosis;

import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * {@link DiagnosisPrice} business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class DiagnosisPriceService {

  /**
   * {@link DiagnosisPriceRepository}.
   */
  private final DiagnosisPriceRepository dpRepository;
  /**
   * {@link SubjectService}.
   */
  private final SubjectService subjectService;

  /**
   * Список {@link DiagnosisPrice}.
   *
   * @param idSubject id {@link Subject}
   * @return список {@link DiagnosisPrice}
   */
  protected List<DiagnosisPrice> getDiagnosisPriceList(final Long idSubject) {
    final Subject subjectById = subjectService.getSubjectById(idSubject);
    return dpRepository.findAllByNameSubject(subjectById.getNameSubject());
  }
}
