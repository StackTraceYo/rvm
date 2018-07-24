package org.stacktrace.yo.rjvm.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ClassLoaderClient {
    private static final Logger myLogger = LoggerFactory.getLogger(ClassLoaderClient.class.getSimpleName());


    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    private Listener myListener;

    public ClassLoaderClient(URI serverUri) {
        this(serverUri, null);
    }

    public ClassLoaderClient(URI serverUri, Listener listener) {
//        super(serverUri);
        myListener = listener;
    }

//    @Override
//    public void onOpen(ServerHandshake handshakedata) {
//        myLogger.debug("[Classloader Client] Connected {}", this.uri);
//        isConnected.getAndSet(true);
//        fireListener(Listener::onOpen);
//    }
//
//    @Override
//    public void onMessage(String message) {
//        fireListener(listener -> listener.onMessage().accept(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8))));
//    }
//
//    @Override
//    public void onMessage(ByteBuffer bytes) {
//        fireListener(listener -> listener.onMessage().accept(bytes));
//    }
//
//    @Override
//    public void onClose(int code, String reason, boolean remote) {
//        myLogger.debug("[Classloader Client] Closing");
//        isConnected.getAndSet(false);
//        fireListener(listener -> listener.onClose().accept(reason));
//    }
//
//    @Override
//    public void onError(Exception ex) {
//        myLogger.debug("[Classloader Client] Errored - Closing", ex);
//        isConnected.getAndSet(false);
//        fireListener(listener -> listener.onError().accept(ex));
//    }

    private void fireListener(Consumer<Listener> consumer) {
        if (myListener != null) {
            consumer.accept(myListener);
        }
    }

    public interface Listener {

        Consumer<Void> onOpen();

//        Consumer<String> onMessage();

        Consumer<ByteBuffer> onMessage();

        Consumer<String> onClose();

        Consumer<Exception> onError();
    }
}
