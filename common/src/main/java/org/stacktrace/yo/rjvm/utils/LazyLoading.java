package org.stacktrace.yo.rjvm.utils;

import java.util.function.Supplier;

public class LazyLoading<Value> implements Supplier<Value> {

    public static <Value> LazyLoading<Value> lazy(Supplier<Value> supplier) {
        return new LazyLoading<>(supplier);
    }

    public static <Value> LazyLoading<Value> lazy(Value val) {
        return new LazyLoading<>(() -> val);
    }


    // no serialization
    private transient volatile boolean initialized;
    private transient final Supplier<Value> mySupplierDelegate;
    private Value myValue;

    public LazyLoading(Supplier<Value> sup) {
        mySupplierDelegate = sup;
    }

    @Override
    // http://en.wikipedia.org/wiki/Double-checked_locking
    public Value get() {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    Value val = mySupplierDelegate.get();
                    myValue = val;
                    initialized = true;
                    return val;
                }
            }
        }
        return myValue;
    }

    @Override
    public String toString() {
        return mySupplierDelegate.toString();
    }
}

