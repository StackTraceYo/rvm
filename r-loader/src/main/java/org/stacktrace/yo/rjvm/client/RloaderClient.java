package org.stacktrace.yo.rjvm.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stacktrace.yo.rjvm.client.channel.RLoaderClientHandler;
import org.stacktrace.yo.rjvm.client.config.RLoaderClientConfig;

import java.util.concurrent.atomic.AtomicBoolean;

public class RloaderClient {

    private static final Logger myLogger = LoggerFactory.getLogger(RloaderClient.class.getSimpleName());
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final RLoaderClientConfig myConfig;
    private EventLoopGroup myEventGroup;
    private Bootstrap myServer;
    private Channel myConnectedChannel;
    private ChannelFuture myLastWrite;
    private RLoaderClientHandler myHandler;

    public RloaderClient(RLoaderClientConfig config) {
        myConfig = config;
    }

    public RloaderClient connect() throws Exception {
        if (!connected.get()) {
            myHandler = new RLoaderClientHandler();
            myEventGroup = new NioEventLoopGroup();
            myServer = new Bootstrap();
            myServer.group(myEventGroup)
                    .channel(NioSocketChannel.class)
                    .handler(myHandler);

            // Create connection
            myConnectedChannel = myServer.connect(myConfig.getAddress()).sync().channel();
            connected.set(true);
        }
        return this;
    }

    public RloaderClient close() throws Exception {
        if (connected.get()) {
            if (myLastWrite != null) {
                myLastWrite.sync();
            }
            myConnectedChannel.closeFuture().sync();
            myEventGroup.shutdownGracefully();
            connected.set(false);
        }
        return this;
    }

    public boolean ready() {
        return this.connected.get();
    }
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
