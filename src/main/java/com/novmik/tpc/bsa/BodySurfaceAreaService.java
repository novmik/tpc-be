package com.novmik.tpc.bsa;

import static com.novmik.tpc.bsa.BodySurfaceAreaConstants.BSA_NOT_CORRECT_1_OPTION;
import static com.novmik.tpc.bsa.BodySurfaceAreaConstants.BSA_NOT_CORRECT_2_OPTIONS;
import static com.novmik.tpc.bsa.BodySurfaceAreaConstants.BSA_REQUEST_NOT_CORRECT;

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
   * Коллекция ППТ.
   *
   * @param bsaRequest Запрос ППТ {@link BodySurfaceAreaRequest}
   * @return коллекция ППТ
   * @throws BadRequestException если некорректные данные
   */
  public static Map<String, Double> allMethods(final BodySurfaceAreaRequest bsaRequest) {
    if (bsaRequest == null || bsaRequest.getWeight() == null) {
      throw new BadRequestException(BSA_REQUEST_NOT_CORRECT + bsaRequest);
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
        height < BodySurfaceAreaConstants.MIN_HEIGHT
        || height > BodySurfaceAreaConstants.MAX_HEIGHT
        || weight < BodySurfaceAreaConstants.MIN_WEIGHT
        || weight > BodySurfaceAreaConstants.MAX_WEIGHT
    ) {
      throw new BadRequestException(
          BSA_NOT_CORRECT_2_OPTIONS + height + "/" + weight);
    }
    return BodySurfaceAreaUtils.allMethods(height, weight);
  }

  private static Map<String, Double> allMethods(final double weight) {
    if (
        weight < BodySurfaceAreaConstants.MIN_WEIGHT
        || weight > BodySurfaceAreaConstants.MAX_WEIGHT
    ) {
      throw new BadRequestException(BSA_NOT_CORRECT_1_OPTION + weight);
    }
    return BodySurfaceAreaUtils.allMethods(weight);
  }

  /**
   * Ctor.
   */
  private BodySurfaceAreaService() {
  }
}
