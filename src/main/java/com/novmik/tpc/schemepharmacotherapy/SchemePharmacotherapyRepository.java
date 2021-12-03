package com.novmik.tpc.schemepharmacotherapy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemePharmacotherapyRepository extends
    JpaRepository<SchemePharmacotherapy, Long> {

  Optional<SchemePharmacotherapy> findByCodeScheme(String codeScheme);
}
