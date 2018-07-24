package org.stacktrace.yo.rjvm.client.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.stacktrace.yo.proto.rloader.RLoader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RLoaderClientHandler extends SimpleChannelInboundHandler<RLoader.RLoaderResponse> {

    private Channel channel;
    BlockingQueue<RLoader.RLoaderResponse> responses = new LinkedBlockingQueue<>();

    public void send(){
        channel.writeAndFlush("dsf");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RLoader.RLoaderResponse msg) throws Exception {
        responses.add(msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
