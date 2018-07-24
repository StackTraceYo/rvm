package org.stacktrace.yo.rjvm.utils.async;


import io.netty.util.concurrent.Future;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Futures class has utility methods for dealing with various "Futures".
 */
public class Futures {

    /**
     * This method accepts a list of {@link CompletableFuture} and converts it into a single CompletableFuture
     * of a stream of the same type, that completes only when the collection of futures complete.
     *
     * @param <T>     the type parameter
     * @param futures the future collection
     * @return the completable future
     */
    public static <T> CompletableFuture<Stream<T>> ofAllStream(Collection<CompletableFuture<T>> futures) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join));
    }

    /**
     * This method accepts a list of {@link CompletableFuture} and converts it into a single CompletableFuture
     * of the same type, that completes only when the collection of futures complete
     *
     * @param <T>     the type parameter
     * @param futures the future collection
     * @return the completable future
     */
    public static <T> CompletableFuture<List<T>> ofAll(Collection<CompletableFuture<T>> futures) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    /**
     * This method accepts a list of {@link CompletableFuture} and converts it into a single CompletableFuture
     * of a stream of the same type, that completes only when the collection's futures complete.
     *
     * @param <T>     the type parameter
     * @param futures the future collection
     * @param ex      the executor to complete the futures on
     * @return the completable future
     */
    public static <T> CompletableFuture<Stream<T>> ofAllStream(Collection<CompletableFuture<T>> futures, Executor ex) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApplyAsync(v -> futures.stream().map(CompletableFuture::join), ex);
    }

    /**
     * This method accepts a list of {@link CompletableFuture} and converts it into a single CompletableFuture
     * of the same type, that completes only when the collection's futures complete
     *
     * @param <T>     the type parameter
     * @param futures the future collection
     * @param ex      the executor to complete the futures
     * @return the completable future
     */
    public static <T> CompletableFuture<List<T>> ofAll(Collection<CompletableFuture<T>> futures, Executor ex) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApplyAsync(v -> futures.stream().map(CompletableFuture::join)
                        .collect(Collectors.toList()), ex);
    }

    public static <T> CompletableFuture<T> toJava(Future<T> nettyFuture) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        nettyFuture.addListener((Future<T> f) -> {
            if (f.isSuccess()) {
                future.complete(f.getNow());
            } else {
                future.completeExceptionally(f.cause());
            }
        });

        return future;
    }
}
