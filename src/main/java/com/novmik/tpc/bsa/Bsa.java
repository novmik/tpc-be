package com.novmik.tpc.bsa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Bsa {

  public static double mosteller(final int height, final double weight) {
    return Math.sqrt(height * weight) / 60;
  }

  public static double dubois(final int height, final double weight) {
    return 0.007_184 * Math.pow(weight, 0.425) * Math.pow(height, 0.725);
  }

  public static double haycock(final int height, final double weight) {
    return 0.024_265 * Math.pow(weight, 0.537_8) * Math.pow(height, 0.396_4);
  }

  public static double gehangeorge(final int height, final double weight) {
    return 0.023_5 * Math.pow(weight, 0.514_56) * Math.pow(height, 0.422_46);
  }

  public static double fujimoto(final int height, final double weight) {
    return 0.008_883 * Math.pow(weight, 0.444) * Math.pow(height, 0.663);
  }

  public static double takahira(final int height, final double weight) {
    return 0.007_241 * Math.pow(weight, 0.425) * Math.pow(height, 0.725);
  }

  public static double shuteraslani(final int height, final double weight) {
    return 0.009_49 * Math.pow(weight, 0.441) * Math.pow(height, 0.655);
  }

  public static double mattara(final int height, final double weight) {
    return (height + weight - 60) / 100;
  }

  public static double costeff(final double weight) {
    return (4 * weight + 7) / (weight + 90);
  }

  public static double livingstonscott(final double weight) {
    return 0.117_3 * Math.pow(weight, 0.646_6);
  }

  public static Map<String, Double> allMethods(final int height, final double weight) {
    Map<String, Double> bsaMethodMap = new HashMap<>();
    if (height == 0 || weight == 0) {
      bsaMethodMap = Collections.emptyMap();
    } else {
      bsaMethodMap.put("Мостеллера", mosteller(height, weight));
      bsaMethodMap.put("Дюбуа и Дюбуа", dubois(height, weight));
      bsaMethodMap.put("Хейкока", haycock(height, weight));
      bsaMethodMap.put("Гехана и Джорджа", gehangeorge(height, weight));
      bsaMethodMap.put("Фудзимото", fujimoto(height, weight));
      bsaMethodMap.put("Такахира", takahira(height, weight));
      bsaMethodMap.put("Шутера и Аслани", shuteraslani(height, weight));
      bsaMethodMap.put("Маттара", mattara(height, weight));
      bsaMethodMap.put("Костеффа", costeff(weight));
      bsaMethodMap.put("Ливингстона и Скотта", livingstonscott(weight));
    }
    return bsaMethodMap;
  }

  public static Map<String, Double> allMethods(final double weight) {
    Map<String, Double> bsaMethodMap = new HashMap<>();
    if (weight == 0) {
      bsaMethodMap = Collections.emptyMap();
    } else {
      bsaMethodMap.put("Костеффа", costeff(weight));
      bsaMethodMap.put("Ливингстона и Скотта", livingstonscott(weight));
    }
    return bsaMethodMap;
  }

  private Bsa() {
  }
}
