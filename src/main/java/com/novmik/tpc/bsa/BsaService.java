package com.novmik.tpc.bsa;

import static com.novmik.tpc.bsa.BsaConstant.BSA_NOT_CORRECT_1_OPTION;
import static com.novmik.tpc.bsa.BsaConstant.BSA_NOT_CORRECT_2_OPTIONS;
import static com.novmik.tpc.bsa.BsaConstant.BSA_REQUEST_NOT_CORRECT;
import static com.novmik.tpc.bsa.BsaConstant.MAX_HEIGHT;
import static com.novmik.tpc.bsa.BsaConstant.MAX_WEIGHT;
import static com.novmik.tpc.bsa.BsaConstant.MIN_HEIGHT;
import static com.novmik.tpc.bsa.BsaConstant.MIN_WEIGHT;

import com.novmik.tpc.exception.BadRequestException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public final class BsaService {

  public static Map<String, Double> allMethods(final BsaRequest bsaRequest) {
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
    if (height < MIN_HEIGHT || height > MAX_HEIGHT
        || weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
      throw new BadRequestException(
          BSA_NOT_CORRECT_2_OPTIONS + height + "/" + weight);
    }
    return Bsa.allMethods(height, weight);
  }

  private static Map<String, Double> allMethods(final double weight) {
    if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
      throw new BadRequestException(BSA_NOT_CORRECT_1_OPTION + weight);
    }
    return Bsa.allMethods(weight);
  }

  private BsaService() {
  }
}
