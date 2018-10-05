package org.stacktrace.yo.rjvm;

import org.stacktrace.yo.rjvm.config.RloaderServerConfig;

public final class RLoaderRunner {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8889"));

    public static void main(String[] args) throws Exception {
        final RLoaderServer myServer = new RLoaderServer(
                RloaderServerConfig.builder()
                        .setPort(PORT)
                        .setNumOfThreads(5)
                        .build()
        );
        myServer.run();
    }

}
