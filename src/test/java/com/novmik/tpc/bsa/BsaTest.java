package com.novmik.tpc.bsa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BsaTest {

  @Test
  void canCountMosteller() {
    assertThat(Bsa.mosteller(170, 70)).isEqualTo(1.818118685772619);
  }

  @Test
  void canCountDubois() {
    assertThat(Bsa.dubois(170, 70)).isEqualTo(1.809707801753247);
  }

  @Test
  void canCountHaycock() {
    assertThat(Bsa.haycock(170, 70)).isEqualTo(1.8256771247769754);
  }

  @Test
  void canCountGehangeorge() {
    assertThat(Bsa.gehangeorge(170, 70)).isEqualTo(1.8312893134221293);
  }

  @Test
  void canCountFujimoto() {
    assertThat(Bsa.fujimoto(170, 70)).isEqualTo(1.7642934701397994);
  }

  @Test
  void canCountTakahira() {
    assertThat(Bsa.takahira(170, 70)).isEqualTo(1.8240665635433273);
  }

  @Test
  void canCountShuteraslani() {
    assertThat(Bsa.shuteraslani(170, 70)).isEqualTo(1.7860700647202692);
  }

  @Test
  void canCountMattara() {
    assertThat(Bsa.mattara(170, 70)).isEqualTo(1.8);
  }

  @Test
  void canCountCosteff() {
    assertThat(Bsa.costeff(70)).isEqualTo(1.79375);
  }

  @Test
  void canCountLivingstonscott() {
    assertThat(Bsa.livingstonscott(70)).isEqualTo(1.8295252375947064);
  }

  @Test
  void canGetAllMethodsWhenTwoParams() {
    assertThat(Bsa.allMethods(170, 70)).isNotNull();
  }

  @Test
  void getEmptyWhenTwoParamsIsNull() {
    assertThat(Bsa.allMethods(0, 0)).isEmpty();
  }

  @Test
  void canGetAllMethodsWhenOneParams() {
    assertThat(Bsa.allMethods(70)).isNotNull();
  }

  @Test
  void getEmptyWhenOneParamsIsNull() {
    assertThat(Bsa.allMethods(0)).isEmpty();
  }
}