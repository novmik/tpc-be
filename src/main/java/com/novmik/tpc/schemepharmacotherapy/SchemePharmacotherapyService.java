package com.novmik.tpc.schemepharmacotherapy;

import com.novmik.tpc.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapyConstant.SCHEME_PHARMACOTHERAPY_NOT_EXISTS;

@AllArgsConstructor
@Service
public class SchemePharmacotherapyService {

    private final SchemePharmacotherapyRepository schemePharmacotherapyRepository;

    public Optional<SchemePharmacotherapy> findByCodeScheme(String codeScheme) {
        Optional<SchemePharmacotherapy> byCodeScheme = schemePharmacotherapyRepository.findByCodeScheme(codeScheme);
        if (byCodeScheme.isEmpty()) {
            throw new NotFoundException(SCHEME_PHARMACOTHERAPY_NOT_EXISTS + codeScheme);
        }
        return byCodeScheme;
    }



}
