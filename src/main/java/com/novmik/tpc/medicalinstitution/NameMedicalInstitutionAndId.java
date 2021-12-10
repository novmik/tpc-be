package com.novmik.tpc.medicalinstitution;

/**
 * Наименование {@link MedicalInstitution} и id.
 */
public interface NameMedicalInstitutionAndId {

  /**
   * id {@link MedicalInstitution}.
   *
   * @return id {@link MedicalInstitution}
   */
  Long getId();

  /**
   * Наименование {@link MedicalInstitution}.
   *
   * @return наименование {@link MedicalInstitution}
   */
  @SuppressWarnings("PMD.MethodNamingConventions")
  String getName_mi();
}