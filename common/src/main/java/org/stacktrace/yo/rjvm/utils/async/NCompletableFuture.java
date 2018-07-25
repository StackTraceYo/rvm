package org.stacktrace.yo.rjvm.utils.async;

import io.netty.util.concurrent.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class NCompletableFuture<T> extends CompletableFuture<T> {

    public static <T> NCompletableFuture<T> of(Future<T> nettyFuture) {
        return new NCompletableFuture<>(nettyFuture);
    }

    private final Future<T> myNettyFuture;

    private NCompletableFuture(Future<T> nettyFuture) {
        myNettyFuture = nettyFuture;
        setup();
    }

    private void setup() {
        myNettyFuture.addListener((Future<T> f) -> {
            if (f.isSuccess()) {
                complete(f.getNow());
            } else {
                completeExceptionally(f.cause());
            }
        });
    }

    @Override
    public T join() {
        if (isDone()) {
            return myNettyFuture.getNow();
        } else {
            try {
                return myNettyFuture.get();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return myNettyFuture.cancel(mayInterruptIfRunning);
    }
}
