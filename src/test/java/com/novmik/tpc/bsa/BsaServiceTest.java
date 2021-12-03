package com.novmik.tpc.bsa;

import static com.novmik.tpc.bsa.BsaConstant.BSA_NOT_CORRECT_1_OPTION;
import static com.novmik.tpc.bsa.BsaConstant.BSA_NOT_CORRECT_2_OPTIONS;
import static com.novmik.tpc.bsa.BsaConstant.BSA_REQUEST_NOT_CORRECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.novmik.tpc.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class BsaServiceTest {

  @Test
  void canGetMapWithAllBsaMethodsWhenTwoOptions() {
    BsaRequest bsaRequest = new BsaRequest(170, 70.0);
    assertThat(BsaService.allMethods(bsaRequest)).isNotEmpty();
    assertThat(BsaService.allMethods(bsaRequest).size()).isGreaterThanOrEqualTo(10);
  }

  @Test
  void canGetMapWithAllBsaMethodsWhenHeightIsNull() {
    BsaRequest bsaRequest = new BsaRequest(null, 70.0);
    assertThat(BsaService.allMethods(bsaRequest)).isNotEmpty();
    assertThat(BsaService.allMethods(bsaRequest).size()).isGreaterThanOrEqualTo(2);
  }

  @Test
  void canGetMapWithAllBsaMethodsWhenOneOptions() {
    BsaRequest bsaRequest = new BsaRequest(null, -70.0);
    assertThatThrownBy(() -> BsaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(BSA_NOT_CORRECT_1_OPTION);
  }

  @Test
  void canGetMapWithAllBsaMethodsWhenWeightIsZero() {
    BsaRequest bsaRequest = new BsaRequest(170, 0.0);
    assertThatThrownBy(() -> BsaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(BSA_NOT_CORRECT_2_OPTIONS);
  }

  @Test
  void willThrowWhenGetMapWithAllBsaMethodsWithNullOptions() {
    BsaRequest bsaRequest = new BsaRequest();
    assertThatThrownBy(() -> BsaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(BSA_REQUEST_NOT_CORRECT);
  }

  @Test
  void willThrowWhenGetMapWithAllBsaMethodsWithNullWeight() {
    BsaRequest bsaRequest = new BsaRequest(170, null);
    assertThatThrownBy(() -> BsaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(BSA_REQUEST_NOT_CORRECT);
  }


}