package com.novmik.tpc.bsa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Bsa {

    public static double mosteller(int height, double weight) {
        return Math.sqrt(height * weight) / 60;
    }

    public static double dubois(int height, double weight) {
        return 0.007184 * Math.pow(weight, 0.425) * Math.pow(height, 0.725);
    }

    public static double haycock(int height, double weight) {
        return 0.024265 * Math.pow(weight, 0.5378) * Math.pow(height, 0.3964);
    }

    public static double gehangeorge(int height, double weight) {
        return 0.0235 * Math.pow(weight, 0.51456) * Math.pow(height, 0.42246);
    }

    public static double fujimoto(int height, double weight) {
        return 0.008883 * Math.pow(weight, 0.444) * Math.pow(height, 0.663);
    }

    public static double takahira(int height, double weight) {
        return 0.007241 * Math.pow(weight, 0.425) * Math.pow(height, 0.725);
    }

    public static double shuteraslani(int height, double weight) {
        return 0.00949 * Math.pow(weight, 0.441) * Math.pow(height, 0.655);
    }

    public static double mattara(int height, double weight) {
        return (height + weight - 60) / 100;
    }

    public static double costeff(double weight) {
        return (4 * weight + 7) / (weight + 90);
    }

    public static double livingstonscott(double weight) {
        return 0.1173 * Math.pow(weight, 0.6466);
    }

    public static Map<String, Double> allMethods(int height, double weight) {
        if (height == 0 || weight == 0) {
            return Collections.emptyMap();
        }
        Map<String, Double> bsaMethodMap = new HashMap<>();
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
        return bsaMethodMap;
    }

    public static Map<String, Double> allMethods(double weight) {
        if (weight == 0) {
            return Collections.emptyMap();
        }
        Map<String, Double> bsaMethodMap = new HashMap<>();
        bsaMethodMap.put("Костеффа", costeff(weight));
        bsaMethodMap.put("Ливингстона и Скотта", livingstonscott(weight));
        return bsaMethodMap;
    }

    private Bsa() {
    }
}
