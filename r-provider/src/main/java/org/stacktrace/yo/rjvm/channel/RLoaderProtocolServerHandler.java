package org.stacktrace.yo.rjvm.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stacktrace.yo.proto.rloader.RLoader;

public class RLoaderProtocolServerHandler extends SimpleChannelInboundHandler<RLoader.RLoaderRequest> {

    private static final Logger myLogger = LoggerFactory.getLogger(RLoaderProtocolServerHandler.class.getSimpleName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RLoader.RLoaderRequest msg) throws Exception {
        myLogger.info(msg.getMessageCase().toString());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
