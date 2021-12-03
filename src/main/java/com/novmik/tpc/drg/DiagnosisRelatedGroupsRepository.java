package com.novmik.tpc.drg;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRelatedGroupsRepository extends
    JpaRepository<DiagnosisRelatedGroups, Long> {

  Optional<DiagnosisRelatedGroups> findByNumberDrg(String numberDrg);
}
