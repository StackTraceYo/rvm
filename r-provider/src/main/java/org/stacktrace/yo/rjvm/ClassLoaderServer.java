package org.stacktrace.yo.rjvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stacktrace.yo.rjvm.provider.RemoteClassProvider;

import java.net.InetSocketAddress;

public class ClassLoaderServer {

    private final RemoteClassProvider myClassProvider;

    private static final Logger myLogger = LoggerFactory.getLogger(ClassLoaderServer.class.getSimpleName());

    public ClassLoaderServer(InetSocketAddress address) {
//        super(address);
        myClassProvider = new RemoteClassProvider();
    }

    public ClassLoaderServer(Integer port) {
        this(new InetSocketAddress(port));
    }

//    @Override
//    public void onStart() {
//        myLogger.debug("Server Started");
//    }
//
//    @Override
//    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
//        myLogger.debug("[ClassLoader Server] Got Connection {}", clientHandshake.getResourceDescriptor());
//        conn.send(
//                RLoader.ConnectionRecieved.newBuilder()
//                        .setReady(true)
//                        .build()
//                        .toByteArray()
//        );
//        myLogger.debug("[ClassLoader Server] Sending Connection");
//    }
//
//    @Override
//    public void onClose(WebSocket conn, int i, String s, boolean b) {
//        myLogger.debug("[ClassLoader Server] Connection Closed {}", conn.getResourceDescriptor());
//    }
//
//    @Override
//    public void onMessage(WebSocket conn, String s) {
//        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes(StandardCharsets.UTF_8));
//        myLogger.debug("[ClassLoader Server] String Message Received -> Converting");
//        onMessage(conn, buffer);
//    }
//
//    @Override
//    public void onMessage(WebSocket conn, ByteBuffer buffer) {
//        myLogger.debug("[ClassLoader Server] Message Received");
//        try {
//            RLoader.RLoaderMessage message = RLoader.RLoaderMessage
//                    .parseFrom(buffer);
//            switch (message.getMessageCase()) {
//                case LOADCLASS:
//                    RLoader.ClassLoaded classLoaded = myClassProvider.findClass(message.getLoadClass());
//                    conn.send(
//                            classLoaded
//                                    .toByteArray()
//                    );
//                case FINISHEDLOADING:
//                case MESSAGE_NOT_SET:
//
//            }
//
//        } catch (InvalidProtocolBufferException e) {
//        }
//    }
//
//    @Override
//    public void onError(WebSocket conn, Exception e) {
//        myLogger.error("[ClassLoader Server] Error", e);
//
//    }
}
