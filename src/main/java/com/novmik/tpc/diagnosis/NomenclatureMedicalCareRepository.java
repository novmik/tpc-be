package com.novmik.tpc.diagnosis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomenclatureMedicalCareRepository extends
    JpaRepository<NomenclatureMedicalCare, Long> {

}
