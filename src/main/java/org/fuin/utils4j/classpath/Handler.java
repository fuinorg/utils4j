package org.fuin.utils4j.classpath;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Allows reading resources from the classpath using the 'classpath:" protocol.
 */
public final class Handler extends URLStreamHandler {

    static final String HANDLER_PKGS = "java.protocol.handler.pkgs";
    
    static final String PKG = "org.fuin.utils4j"; 

    /**
     * Adds the protocol handler.
     */
    public static void add() {

        String current = System.getProperties().getProperty(HANDLER_PKGS, "");
        if (current.length() > 0) {
            if (current.contains(PKG)) {
                return;
            }
            current = current + "|";
        }
        System.getProperties().put(HANDLER_PKGS, current + PKG);

    }

    /**
     * Removes the protocol handler.
     */
    public static void remove() {

        final String current = System.getProperties().getProperty(HANDLER_PKGS, "");

        // Only one
        if (current.equals(PKG)) {
            System.getProperties().put(HANDLER_PKGS, "");
            return;
        }

        // Middle
        String token = "|" + PKG + "|";
        int idx = current.indexOf(token);
        if (idx > -1) {
            final String removed = current.substring(0, idx) + "|" + current.substring(idx + token.length());
            System.getProperties().put(HANDLER_PKGS, removed);
            return;
        }

        // Beginning
        token = PKG + "|";
        idx = current.indexOf(token);
        if (idx > -1) {
            final String removed = current.substring(idx + token.length());
            System.getProperties().put(HANDLER_PKGS, removed);
            return;
        }

        // End
        token = "|" + PKG;
        idx = current.indexOf(token);
        if (idx > -1) {
            final String removed = current.substring(0, idx);
            System.getProperties().put(HANDLER_PKGS, removed);
            return;
        }

    }

    @Override
    protected final URLConnection openConnection(final URL url) throws IOException {
        final String path = url.getPath();
        final URL resourceUrl = this.getClass().getResource(path);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return resourceUrl.openConnection();
    }

}
