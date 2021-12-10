package com.novmik.tpc.subject;


/**
 * Constants для пакета subject.
 */
@SuppressWarnings("PMD.LongVariable")
public final class SubjectConstants {

  /**
   * SUBJECT_EXISTS.
   */
  public static final String SUBJECT_EXISTS =
      "Субъект с таким id/именем/названием уже существует: ";
  /**
   * SUBJECT_NOT_EXISTS.
   */
  public static final String SUBJECT_NOT_EXISTS =
      "Субъекта с таким id/именем/названием не существует: ";
  /**
   * SUBJECT_NOT_CORRECT.
   */
  public static final String SUBJECT_NOT_CORRECT =
      "Некорректные данные о субъекте.";

  /**
   * Ctor.
   */
  private SubjectConstants() {
  }

}
