package com.novmik.tpc.bsa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.novmik.tpc.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class BodySurfaceAreaServiceTest {

  @Test
  void canGetMapWithAllBsaMethodsWhenTwoOptions() {
    BodySurfaceAreaRequest bsaRequest = new BodySurfaceAreaRequest(170, 70.0);
    assertThat(BodySurfaceAreaService.allMethods(bsaRequest)).isNotEmpty();
    assertThat(BodySurfaceAreaService.allMethods(bsaRequest).size()).isGreaterThanOrEqualTo(10);
  }

  @Test
  void canGetMapWithAllBsaMethodsWhenHeightIsNull() {
    BodySurfaceAreaRequest bsaRequest = new BodySurfaceAreaRequest(null, 70.0);
    assertThat(BodySurfaceAreaService.allMethods(bsaRequest)).isNotEmpty();
    assertThat(BodySurfaceAreaService.allMethods(bsaRequest).size()).isGreaterThanOrEqualTo(2);
  }

  @Test
  void canGetMapWithAllBsaMethodsWhenOneOptions() {
    BodySurfaceAreaRequest bsaRequest = new BodySurfaceAreaRequest(null, -70.0);
    assertThatThrownBy(() -> BodySurfaceAreaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessage(String.format(
            "Некорректный параметр(вес) ППТ: %s", bsaRequest.getWeight()));
  }

  @Test
  void canGetMapWithAllBsaMethodsWhenWeightIsZero() {
    BodySurfaceAreaRequest bsaRequest = new BodySurfaceAreaRequest(170, 0.0);
    assertThatThrownBy(() -> BodySurfaceAreaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Некорректные параметры(рост или вес) ППТ: ");
  }

  @Test
  void willThrowWhenGetMapWithAllBsaMethodsWithNullOptions() {
    BodySurfaceAreaRequest bsaRequest = new BodySurfaceAreaRequest();
    assertThatThrownBy(() -> BodySurfaceAreaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Некорректные параметры(рост или вес): ");
  }

  @Test
  void willThrowWhenGetMapWithAllBsaMethodsWithNullWeight() {
    BodySurfaceAreaRequest bsaRequest = new BodySurfaceAreaRequest(170, null);
    assertThatThrownBy(() -> BodySurfaceAreaService.allMethods(bsaRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Некорректные параметры(рост или вес): ");
  }


}