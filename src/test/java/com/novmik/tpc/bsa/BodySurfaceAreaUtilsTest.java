package com.novmik.tpc.bsa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BodySurfaceAreaUtilsTest {

  @Test
  void canCountMosteller() {
    assertThat(BodySurfaceAreaUtils.mosteller(170, 70)).isEqualTo(1.818118685772619);
  }

  @Test
  void canCountDubois() {
    assertThat(BodySurfaceAreaUtils.dubois(170, 70)).isEqualTo(1.809707801753247);
  }

  @Test
  void canCountHaycock() {
    assertThat(BodySurfaceAreaUtils.haycock(170, 70)).isEqualTo(1.8256771247769754);
  }

  @Test
  void canCountGehangeorge() {
    assertThat(BodySurfaceAreaUtils.gehangeorge(170, 70)).isEqualTo(1.8312893134221293);
  }

  @Test
  void canCountFujimoto() {
    assertThat(BodySurfaceAreaUtils.fujimoto(170, 70)).isEqualTo(1.7642934701397994);
  }

  @Test
  void canCountTakahira() {
    assertThat(BodySurfaceAreaUtils.takahira(170, 70)).isEqualTo(1.8240665635433273);
  }

  @Test
  void canCountShuteraslani() {
    assertThat(BodySurfaceAreaUtils.shuteraslani(170, 70)).isEqualTo(1.7860700647202692);
  }

  @Test
  void canCountMattara() {
    assertThat(BodySurfaceAreaUtils.mattara(170, 70)).isEqualTo(1.8);
  }

  @Test
  void canCountCosteff() {
    assertThat(BodySurfaceAreaUtils.costeff(70)).isEqualTo(1.79375);
  }

  @Test
  void canCountLivingstonscott() {
    assertThat(BodySurfaceAreaUtils.livingstonscott(70)).isEqualTo(1.8295252375947064);
  }

  @Test
  void canGetAllMethodsWhenTwoParams() {
    assertThat(BodySurfaceAreaUtils.allMethods(170, 70)).isNotNull();
  }

  @Test
  void getEmptyWhenTwoParamsIsNull() {
    assertThat(BodySurfaceAreaUtils.allMethods(0, 0)).isEmpty();
  }

  @Test
  void canGetAllMethodsWhenOneParams() {
    assertThat(BodySurfaceAreaUtils.allMethods(70)).isNotNull();
  }

  @Test
  void getEmptyWhenOneParamsIsNull() {
    assertThat(BodySurfaceAreaUtils.allMethods(0)).isEmpty();
  }
}