package org.stacktrace.yo.rjvm;

public class RLoaderRunner {

    private final ClassLoaderServer myServer;

    public RLoaderRunner() {
        myServer = new ClassLoaderServer(8889);
//        myServer.start();
    }

    public static void main(String... args) {
        RLoaderRunner loader = new RLoaderRunner();
    }

}
