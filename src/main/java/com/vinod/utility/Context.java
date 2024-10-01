package com.vinod.utility;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Context {
    private static final Map<String, Object> scenarioContext= new ConcurrentHashMap<>();

    public static void set(Key key, Object value) {
        scenarioContext.put(key.name(), value);
    }

    @SuppressWarnings("unused")
    public static  <T> void addToList(Key key, List<T> values) {
        //noinspection unchecked
        List<T> existValues = (List<T>) Optional.ofNullable(scenarioContext.get(key.name())).orElse(new ArrayList<>());
        existValues.addAll(values);
        set(key, existValues);
    }

    @SuppressWarnings("unused")
    public static  <T> void addToList(Key key, T value) {
        //noinspection unchecked
        List<T> existValues = (List<T>) Optional.ofNullable(scenarioContext.get(key.name())).orElse(new ArrayList<>());
        existValues.add(value);
        set(key, existValues);
    }

    public static  <T> T get(Key key) {
        //noinspection unchecked
        return (T) Optional.ofNullable(scenarioContext.get(key.name())).orElse(null);
    }
}
