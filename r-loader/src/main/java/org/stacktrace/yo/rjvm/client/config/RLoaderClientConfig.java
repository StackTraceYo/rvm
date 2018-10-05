package org.stacktrace.yo.rjvm.client.config;

import java.net.InetSocketAddress;

public class RLoaderClientConfig {


    private final InetSocketAddress myAddress;


    private RLoaderClientConfig(InetSocketAddress myAddress) {
        this.myAddress = myAddress;
    }

    public static Builder builder() {
        return new Builder();
    }

    public InetSocketAddress getAddress() {
        return myAddress;
    }

    public static final class Builder {

        private Integer port;
        private String host;


        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        public RLoaderClientConfig build() {
            InetSocketAddress address;
            if (this.port != null && this.host != null) {
                address = new InetSocketAddress(host, port);
            } else {
                throw new IllegalArgumentException("Port is Null");
            }
            return new RLoaderClientConfig(address);
        }
    }
}
