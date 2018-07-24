package org.stacktrace.yo.rjvm;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stacktrace.yo.rjvm.channel.RloaderChannelInitializer;
import org.stacktrace.yo.rjvm.config.RloaderServerConfig;

public class RLoaderServer {


    private static final Logger myLogger = LoggerFactory.getLogger(RLoaderServer.class.getSimpleName());
    private final EventLoopGroup myServerGroup;
    private final EventLoopGroup myWorkerLoopGroup;
    private final ServerBootstrap myServer;
    private final RloaderServerConfig myConfig;

    public RLoaderServer(RloaderServerConfig config) {
        myConfig = config;
        myServerGroup = new NioEventLoopGroup(
                config.getNumOfThreads()
        );
        myWorkerLoopGroup = new NioEventLoopGroup();
        myServer = new ServerBootstrap()
                .group(myServerGroup, myWorkerLoopGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new RloaderChannelInitializer());
    }

    public void run() throws InterruptedException {
        try {
            myServer.bind(myConfig.getAddress())
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } finally {
            myServerGroup.shutdownGracefully();
            myWorkerLoopGroup.shutdownGracefully();
        }
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
