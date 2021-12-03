package com.novmik.tpc.cdt;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseCdtRepository extends JpaRepository<CaseCdt, Integer> {

  Optional<CaseCdt> findByNominationCaseCdt(String nominationCaseCdt);
}
