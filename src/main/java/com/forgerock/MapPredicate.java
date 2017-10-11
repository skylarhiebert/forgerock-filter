package com.forgerock;

import java.util.Map;
import java.util.function.Predicate;

public class MapPredicate {

    public static Predicate<Map<String, String>> contains(final String key) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return "contains " + key; }
            public boolean test(Map<String, String> p) { return p.containsKey(key); }
        };
    }

    public static Predicate<Map<String, String>> equals(final String key, final String value) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return key + " equals " + value; }
            public boolean test(Map<String, String> p) { return p.get(key).equals(value); }
        };
    }

    public static Predicate<Map<String, String>> equals(final String key, final int value) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return key + " equals " + value; }
            public boolean test(Map<String, String> p) { return Integer.parseInt(p.get(key)) == value; }
        };
    }

    public static Predicate<Map<String, String>> lessThan(final String key, final int value) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return key + " < " + value; }
            public boolean test(Map<String, String> p) { return Integer.parseInt(p.get(key)) < value; }
        };
    }

    public static Predicate<Map<String, String>> lessThanOrEqual(final String key, final int value) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return key + " <= " + value; }
            public boolean test(Map<String, String> p) { return Integer.parseInt(p.get(key)) <= value; }
        };
    }

    public static Predicate<Map<String, String>> greaterThan(final String key, final int value) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return key + " > " + value; }
            public boolean test(Map<String, String> p) { return Integer.parseInt(p.get(key)) > value; }
        };
    }

    public static Predicate<Map<String, String>> greaterThanOrEqual(final String key, final int value) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return key + " >= " + value; }
            public boolean test(Map<String, String> p) { return Integer.parseInt(p.get(key)) >= value; }
        };
    }

    public static Predicate<Map<String, String>> matches(final String key, final String regex) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return key + " matches " + regex; }
            public boolean test(Map<String, String> p) { return p.get(key).matches(regex); }
        };
    }

    public static Predicate<Map<String, String>> isTrue(final String key) {
        return new Predicate<Map<String, String>>() {
            public String toString() { return "isTrue " + key; }
            public boolean test(Map<String, String> p) { return Boolean.parseBoolean(p.get(key)); }
        };
    }
}
