package com.forgerock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

class FilterTest {
    Logger log = Logger.getLogger(FilterTest.class.getName());
    Map<String, String> map;

    String testName;

    @BeforeEach
    void setup(TestInfo info) {
        testName = info.getDisplayName();
        map = new LinkedHashMap<String, String>();
    }

    @Test
    void Stringify() {
        Filter filter = new Filter<Map<String, String>>(MapPredicate.contains("role").and(MapPredicate.contains("age")));
    }

    @Test
    void booleanLiteralTrue() {
        Filter filter = new Filter<Map<String, String>>(MapPredicate.isTrue("Truthy"));
        map.put("Truthy", "trUe");
        assert filter.test(map);

        map.put("Truthy", "true");
        assert filter.test(map);

        map.put("Truthy", "TRUE");
        assert filter.test(map);

        map.put("Truthy", "false");
        assert !filter.test(map);

        map.put("Truthy", "foo");
        assert !filter.test(map);
    }

    @Test
    void booleanLiteralFalse() {
        Filter filter = new Filter<Map<String, String>>(MapPredicate.isTrue("Falsey"));
        map.put("Falsey", "false");
        assert !filter.test(map);

        map.put("Falsey", "FALSE");
        assert !filter.test(map);

        map.put("Falsey", "faLse");
        assert !filter.test(map);
    }

    @Test
    void logicalOperatorAND() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.equals("role", "administrator").and(MapPredicate.greaterThan("age", 30)));
        assert filter.test(map);

        map.put("role", "administrator");
        map.put("age", "25");
        assert !filter.test(map);

        map.put("role", "greenhown");
        map.put("age", "35");
        assert !filter.test(map);

        map.put("role", "greenhorn");
        map.put("age", "25");
        assert !filter.test(map);
    }

    @Test
    void logicalOperatorOR() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.equals("role", "greenhorn").or(MapPredicate.greaterThan("age", 30)));
        assert filter.test(map);

        map.put("role", "greenhorn");
        map.put("age", "25");
        assert filter.test(map);

        map.put("role", "administrator");
        map.put("age", "25");
        assert !filter.test(map);
    }

    @Test
    void logicalOperatorNOT() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.equals("role", "greenhorn").negate());
        assert filter.test(map);

        map.put("role", "greenhorn");
        assert !filter.test(map);
    }

    @Test
    void propertyPresent() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.contains("role"));

        assert filter.test(map);

        map.remove("role");
        assert !filter.test(map);
    }

    @Test
    void propertyEqualString() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.equals("role", "administrator"));
        assert filter.test(map);

        map.put("role", "greenhorn");
        assert !filter.test(map);
    }

    @Test
    void propertyEqualInteger() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.equals("age", 35));
        assert filter.test(map);

        map.put("age", "30");
        assert !filter.test(map);
    }

    @Test
    void propertyGreaterThan() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.greaterThan("age", 30));
        assert filter.test(map);

        map.put("age", "25");
        assert !filter.test(map);

        map.put("age", "30");
        assert !filter.test(map);
    }

    @Test
    void propertyGreaterThanOrEqual() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.greaterThanOrEqual("age", 30));
        assert filter.test(map);

        map.put("age", "25");
        assert !filter.test(map);

        map.put("age", "30");
        assert filter.test(map);
    }

    @Test
    void propertyLessThan() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.lessThan("age", 40));
        assert filter.test(map);

        map.put("age", "45");
        assert !filter.test(map);

        map.put("age", "40");
        assert !filter.test(map);
    }

    @Test
    void propertyLessThanOrEqual() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.lessThanOrEqual("age", 40));
        assert filter.test(map);

        map.put("age", "45");
        assert !filter.test(map);

        map.put("age", "40");
        assert filter.test(map);
    }

    @Test
    void propertyMatchesRegex() {
        map.put("firstname", "Minister");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.matches("firstname", "(.*)inist(.*)"));
        assert filter.test(map);

        map.put("firstname", "administrator");
        assert filter.test(map);
    }

    @Test
    void exampleUsage() {
        map.put("firstname", "Joe");
        map.put("surname", "Bloggs");
        map.put("role", "administrator");
        map.put("age", "35");

        Filter filter = new Filter<Map<String, String>>(MapPredicate.equals("role", "administrator").and(MapPredicate.greaterThan("age", 30)));
        assert filter.test(map);

        map.put("age", "25");
        assert !filter.test(map);
    }
}
