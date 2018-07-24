package org.stacktrace.yo.rjvm.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class RemoteClassLoader extends ClassLoader {
    private static final Logger myLogger = LoggerFactory.getLogger(RemoteClassLoader.class.getSimpleName());
//    private final ClassLoaderClient myClient;

    public RemoteClassLoader(URI serverUri) {
        this(serverUri, Thread.currentThread().getContextClassLoader());
    }

    public RemoteClassLoader(URI serverUri, ClassLoader parent) {
        super(parent);
//        myClient = new ClassLoaderClient(serverUri, this);
//        myClient.connect();
    }

//    @Override
//    public Consumer<Void> onOpen() {
//        return aVoid -> myLogger.debug("[RemoteClassLoader] Opened");
//    }
//
//
//    @Override
//    public Consumer<ByteBuffer> onMessage() {
//        return byteBuffer -> {
//            try {
//                RLoader.RLoaderMessage message = RLoader.RLoaderMessage
//                        .parseFrom(byteBuffer);
//                RLoader.RLoaderMessage.MessageCase messageCase = message.getMessageCase();
//                myLogger.debug(messageCase.toString());
//                switch (messageCase) {
//                    case CONNECTED:
//                        RLoader.ConnectionRecieved connectionRecieved = message.getConnected();
//                        myLogger.debug(connectionRecieved.getId());
//                    case CLASSLOADED:
//
//                }
//            } catch (Exception e) {
//                myLogger.error("[RemoteClassLoader] Error: ", e);
//            }
//        };
//    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

//    @Override
//    public Consumer<String> onClose() {
//        return null;
//    }
//
//    @Override
//    public Consumer<Exception> onError() {
//        return null;
//    }

    public static void main(String... args) throws URISyntaxException {
        RemoteClassLoader x = new RemoteClassLoader(new URI("http://localhost:8889"));

    }
}