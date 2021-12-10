package com.novmik.tpc.subject;

/**
 * Наименование {@link Subject} и id.
 */
public interface NameSubjectAndId {

  /**
   * id {@link Subject}.
   *
   * @return id {@link Subject}
   */
  Long getId();

  /**
   * Наименование {@link Subject}.
   *
   * @return наименование {@link Subject}
   */
  @SuppressWarnings("PMD.MethodNamingConventions")
  String getName_subject();
}
