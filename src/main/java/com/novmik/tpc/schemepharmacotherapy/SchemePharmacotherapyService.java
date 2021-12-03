package com.novmik.tpc.schemepharmacotherapy;

import static com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapyConstant.SCHEME_PHARMACOTHERAPY_NOT_EXISTS;

import com.novmik.tpc.exception.NotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SchemePharmacotherapyService {

  private final SchemePharmacotherapyRepository schemePharmacotherapyRepository;

  public Optional<SchemePharmacotherapy> findByCodeScheme(final String codeScheme) {
    Optional<SchemePharmacotherapy> byCodeScheme = schemePharmacotherapyRepository.findByCodeScheme(
        codeScheme);
    if (byCodeScheme.isEmpty()) {
      throw new NotFoundException(SCHEME_PHARMACOTHERAPY_NOT_EXISTS + codeScheme);
    }
    return byCodeScheme;
  }


}
