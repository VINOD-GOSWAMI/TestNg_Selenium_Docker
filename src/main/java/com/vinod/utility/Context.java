package com.vinod.utility;


import java.util.*;

public class Context {
    private static final ThreadLocal<Map<String, Object>> scenarioContext=  ThreadLocal.withInitial(HashMap::new);

    public static void set(Key key, Object value) {
        scenarioContext.get().put(key.name(), value);
    }

    @SuppressWarnings("unused")
    public static  <T> void addToList(Key key, List<T> values) {
        //noinspection unchecked
        List<T> existValues = (List<T>) Optional.ofNullable(scenarioContext.get().get(key.name())).orElse(new ArrayList<>());
        existValues.addAll(values);
        set(key, existValues);
    }

    @SuppressWarnings("unused")
    public static  <T> void addToList(Key key, T value) {
        //noinspection unchecked
        List<T> existValues = (List<T>) Optional.ofNullable(scenarioContext.get().get(key.name())).orElse(new ArrayList<>());
        existValues.add(value);
        set(key, existValues);
    }

    public static  <T> T get(Key key) {
        //noinspection unchecked
        return (T) Optional.ofNullable(scenarioContext.get().get(key.name())).orElse(null);
    }
}
