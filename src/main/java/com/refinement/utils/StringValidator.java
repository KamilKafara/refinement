package com.refinement.utils;

import java.util.Objects;

public class StringValidator {
    private StringValidator() {
    }

    public static final String EMPTY_STRING = "";

    public static final String NULL_STRING = "null";


    public static boolean codesAreNotNullAndEmpty(String cellCode1, String cellCode2) {
        return codesAreNull(cellCode1, cellCode2) || codesAreNotEmpty(cellCode1, cellCode2);
    }

    private static boolean codesAreNull(String cellCode1, String cellCode2) {
        return Objects.equals(cellCode1, NULL_STRING) && Objects.equals(cellCode2, NULL_STRING);
    }

    private static boolean codesAreNotEmpty(String cellCode1, String cellCode2) {
        return Objects.equals(cellCode1, EMPTY_STRING) && Objects.equals(cellCode2, EMPTY_STRING);
    }
}
