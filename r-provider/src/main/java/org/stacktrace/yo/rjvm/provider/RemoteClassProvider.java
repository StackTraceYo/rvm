package org.stacktrace.yo.rjvm.provider;

import com.google.common.io.ByteStreams;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stacktrace.yo.proto.rloader.RLoader;
import org.stacktrace.yo.rjvm.utils.Hash;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class RemoteClassProvider {

    private static final Logger myLogger = LoggerFactory.getLogger(RemoteClassProvider.class.getSimpleName());
    private final ClassLoaderManager myManager = new ClassLoaderManager();

    public RLoader.ClassLoaded findClass(RLoader.LoadClass loadClass) {
        String resourceName = loadClass.getResource();
        String id = loadClass.getId();
        ClassLoader loader = myManager.get(id);
        try {
            InputStream resourceStream = loader.getResourceAsStream(resourceName);
            byte[] resourceBytes = ByteStreams.toByteArray(resourceStream);
            String hash = Hash.defaultFastHash(resourceBytes);
            return RLoader.ClassLoaded.newBuilder()
                    .setId(id)
                    .setResource(ByteString.copyFrom(resourceBytes))
                    .setHash(hash)
                    .build();

        } catch (IOException e) {
            URL resourceUrl = loader.getResource(resourceName);

        }
        return null;
    }

}
