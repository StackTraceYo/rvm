package org.stacktrace.yo.rjvm.loader;

import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stacktrace.yo.proto.rloader.RLoader;
import org.stacktrace.yo.rjvm.client.RloaderClient;
import org.stacktrace.yo.rjvm.client.config.RLoaderClientConfig;
import org.stacktrace.yo.rjvm.utils.Hash;

import java.net.URI;
import java.net.URISyntaxException;

public class RemoteClassLoader extends ClassLoader {
    private static final Logger myLogger = LoggerFactory.getLogger(RemoteClassLoader.class.getSimpleName());
    private final RloaderClient myClient;

    public RemoteClassLoader(URI serverUri) {
        this(serverUri, Thread.currentThread().getContextClassLoader());
    }

    public RemoteClassLoader(URI serverUri, ClassLoader parent) {
        super(parent);
        myClient = new RloaderClient(RLoaderClientConfig.builder()
                .withHost(serverUri.getHost())
                .withPort(serverUri.getPort())
                .build()
        );
        try {
            myClient.connect();
        } catch (InterruptedException e) {
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            RLoader.ClassLoaded response = myClient.requestClass(
                    RLoader.LoadClass.newBuilder()
                            .setId("123")
                            .setResource(name)
                            .build()
            )
                    .getLoaded();

            if (response.isInitialized()) {
                final String hash = response.getHash();
                final ByteString resource = response.getResource();
                final byte[] bytes = resource.toByteArray();
                if (bytes != null) {
                    //check hash
                    String checked = Hash.defaultFastHash(bytes);
                    boolean eq = checked.equals(hash);
                    if (eq) {
                        int index = name.lastIndexOf(".");
                        if (index > 0) {
                            String packageName = name.substring(0, index);
                            Package pkg = getPackage(packageName);
                            if (pkg == null) {
                                definePackage(packageName, null, null, null, null, null, null, null);
                            }
                        }
                        return defineClass(name, resource.toByteArray(), 0, bytes.length);
                    } else {
                        // retry
                        throw new ClassNotFoundException("Exception Loading Class" + name);
                    }
                } else {
                    //retry
                    throw new ClassNotFoundException("Exception Loading Class" + name);

                }
            } else {
                //retry
                throw new ClassNotFoundException("Exception Loading Class" + name);

            }
        } catch (Exception e) {
            throw new ClassNotFoundException("Exception Loading Class" + name, e);
        }
    }

    public static void main(String... args) throws URISyntaxException {
        RemoteClassLoader x = new RemoteClassLoader(new URI("http://localhost:8889"));

    }
}