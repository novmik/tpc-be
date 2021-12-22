package com.novmik.tpc.bsa;

import com.novmik.tpc.exception.BadRequestException;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * ППТ business interface layer.
 * (Площадь поверхности тела)
 */
@Service
public final class BodySurfaceAreaService {

  /**
   * MIN_HEIGHT.
   */
  public static final int MIN_HEIGHT = 55;
  /**
   * MAX_HEIGHT.
   */
  public static final int MAX_HEIGHT = 500;
  /**
   * MIN_WEIGHT.
   */
  public static final int MIN_WEIGHT = 10;
  /**
   * MAX_WEIGHT.
   */
  public static final int MAX_WEIGHT = 1000;

  /**
   * Коллекция ППТ.
   *
   * @param bsaRequest Запрос ППТ {@link BodySurfaceAreaRequest}
   * @return коллекция ППТ
   * @throws BadRequestException если некорректные данные
   */
  public static Map<String, Double> allMethods(final BodySurfaceAreaRequest bsaRequest) {
    if (bsaRequest == null || bsaRequest.getWeight() == null) {
      throw new BadRequestException(String.format(
          "Некорректные параметры(рост или вес): %s", bsaRequest));
    }
    Map<String, Double> allMethods;
    if (bsaRequest.getHeight() == null) {
      allMethods = allMethods(bsaRequest.getWeight());
    } else {
      allMethods = allMethods(
          bsaRequest.getHeight(),
          bsaRequest.getWeight());
    }
    return allMethods;
  }

  private static Map<String, Double> allMethods(final int height,
      final double weight) {
    if (
        height < MIN_HEIGHT
        || height > MAX_HEIGHT
        || weight < MIN_WEIGHT
        || weight > MAX_WEIGHT
    ) {
      throw new BadRequestException(String.format(
          "Некорректные параметры(рост или вес) ППТ: %s/%s", height, weight));
    }
    return BodySurfaceAreaUtils.allMethods(height, weight);
  }

  private static Map<String, Double> allMethods(final double weight) {
    if (
        weight < MIN_WEIGHT
        || weight > MAX_WEIGHT
    ) {
      throw new BadRequestException(String.format(
          "Некорректный параметр(вес) ППТ: %s", weight));
    }
    return BodySurfaceAreaUtils.allMethods(weight);
  }

  /**
   * Ctor.
   */
  private BodySurfaceAreaService() {
  }
}
