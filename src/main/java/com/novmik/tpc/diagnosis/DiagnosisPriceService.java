package com.novmik.tpc.diagnosis;

import com.novmik.tpc.subject.SubjectOfRF;
import com.novmik.tpc.subject.SubjectOfRFService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DiagnosisPriceService {

    private final DiagnosisPriceRepository diagnosisPriceRepository;
    private final SubjectOfRFService subjectOfRFService;

    protected List<DiagnosisPrice> getDiagnosisPriceList(final Long idSubject) {
        SubjectOfRF subjectById = subjectOfRFService.getSubjectById(idSubject).orElseThrow();
        return diagnosisPriceRepository.findAllByNameSubject(subjectById.getNameSubject());
    }
}
