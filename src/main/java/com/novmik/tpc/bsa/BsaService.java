package com.novmik.tpc.bsa;

import com.novmik.tpc.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.novmik.tpc.bsa.BsaConstant.*;

@Service
public class BsaService {

    public Map<String, Double> allMethods(BsaRequest bsaRequest) {
        if (bsaRequest == null || bsaRequest.getWeight() == null) {
            throw new BadRequestException(BSA_REQUEST_NOT_CORRECT + bsaRequest);
        }
        if (bsaRequest.getHeight() == null) {
            return allMethods(bsaRequest.getWeight());
        }
        return allMethods(bsaRequest.getHeight(), bsaRequest.getWeight());
    }

    private Map<String, Double> allMethods(int height, double weight) {
        if (height < 55 || height > 500 || weight < 10 || weight > 1000) {
            throw new BadRequestException(BSA_NOT_CORRECT_2_OPTIONS + height + "/" + weight);
        }
        return Bsa.allMethods(height, weight);
    }

    private Map<String, Double> allMethods(double weight) {
        if (weight < 10 || weight > 1000) {
            throw new BadRequestException(BSA_NOT_CORRECT_1_OPTION + weight);
        }
        return Bsa.allMethods(weight);
    }
}
