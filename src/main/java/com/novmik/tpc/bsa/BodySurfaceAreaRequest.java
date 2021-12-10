package com.novmik.tpc.bsa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос ППТ.
 * (Площадь поверхности тела)
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BodySurfaceAreaRequest {

  /**
   * Рост человека, см.
   */
  private Integer height;
  /**
   * Вес человека, кг.
   */
  private Double weight;

}
