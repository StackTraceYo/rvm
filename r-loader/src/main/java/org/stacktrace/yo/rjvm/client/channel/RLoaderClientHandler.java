package org.stacktrace.yo.rjvm.client.channel;

import com.google.common.collect.Queues;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import org.stacktrace.yo.proto.rloader.RLoader;

import java.util.concurrent.SynchronousQueue;

public class RLoaderClientHandler extends SimpleChannelInboundHandler<RLoader.RLoaderResponse> {

    private Channel channel;
    private final SynchronousQueue<Promise<RLoader.RLoaderResponse>> queue = Queues.newSynchronousQueue();

    public Promise<RLoader.RLoaderResponse> requestClass(RLoader.LoadClass request) {
        Promise<RLoader.RLoaderResponse> prom = channel.eventLoop().newPromise();
        queue.offer(prom);
        channel.writeAndFlush(request.toByteArray());
        return prom;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RLoader.RLoaderResponse msg) throws Exception {
        queue.remove().setSuccess(msg);
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
