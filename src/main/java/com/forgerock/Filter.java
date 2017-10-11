package com.forgerock;

import java.util.function.Predicate;

/**
 * A defined Predicate for a class object
 * @param <T> the type of objects that this object may be filtered by this object
 */
public class Filter<T> {
    Predicate<T> predicate;

    /**
     * Constructs a new filter instance using the specified class
     * @param predicate The predicate filter to apply
     */
    public Filter(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    /**
     * Sets the predicate filter to apply against
     * @param predicate The predicate filter to apply
     */
    public void setPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    /**
     * The resources to be filtered
     * @param res The resource to be tested with the applied predicate
     * @return The evaluation of the filter applied to {@code res}
     */
    public Boolean test(T res) {
        return predicate.test(res);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return predicate.toString();
    }
}
