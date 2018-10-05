package org.stacktrace.yo.rjvm.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassLoaderManager {

    private final Map<String, ClassLoader> myClassLoaders = new ConcurrentHashMap<>();

    public ClassLoader get(String id) {
        return myClassLoaders.putIfAbsent(id, Thread.currentThread().getContextClassLoader());
    }

}
