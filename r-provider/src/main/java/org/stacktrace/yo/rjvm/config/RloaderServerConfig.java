package org.stacktrace.yo.rjvm.config;

import java.net.InetSocketAddress;

public class RloaderServerConfig {


    private final InetSocketAddress myAddress;
    private final Integer myNumOfThreads;

    private RloaderServerConfig(InetSocketAddress myAddress, Integer myNumOfThreads) {
        this.myAddress = myAddress;
        this.myNumOfThreads = myNumOfThreads;
    }

    public static Builder builder() {
        return new Builder();
    }

    public InetSocketAddress getAddress() {
        return myAddress;
    }

    public Integer getNumOfThreads() {
        return myNumOfThreads;
    }

    public static final class Builder {
        private Integer numOfThreads;

        private Integer port;

        public Builder setNumOfThreads(int numOfThreads) {
            this.numOfThreads = numOfThreads;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public RloaderServerConfig build() {
            InetSocketAddress address;
            if (this.port != null) {
                address = new InetSocketAddress(this.port);
            } else {
                throw new IllegalArgumentException("Port is Null");
            }
            if (this.numOfThreads == null) {
                numOfThreads = 0;
            }
            return new RloaderServerConfig(address, numOfThreads);
        }
    }
}
