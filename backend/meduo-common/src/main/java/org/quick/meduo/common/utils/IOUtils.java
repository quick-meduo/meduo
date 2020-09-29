package org.quick.meduo.common.utils;

import java.io.Closeable;

public class IOUtils {
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final Throwable ioe) {
            //ignore
        }
    }
}