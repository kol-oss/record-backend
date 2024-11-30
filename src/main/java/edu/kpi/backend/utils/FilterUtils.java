package edu.kpi.backend.utils;

public class FilterUtils {
    public static <T> boolean equals(T pattern, T actual) {
        return pattern == null || pattern.equals(actual);
    }
}
