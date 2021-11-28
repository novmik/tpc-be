package com.novmik.tpc.drg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosisRelatedGroupsRepository extends JpaRepository<DiagnosisRelatedGroups, Long> {

    Optional<DiagnosisRelatedGroups> findByNumberDrg (String numberDrg);
}
