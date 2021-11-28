package com.novmik.tpc.cdt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseCdtRepository extends JpaRepository<CaseCdt, Integer> {

    Optional<CaseCdt> findByNominationCaseCdt(String nominationCaseCdt);
}
