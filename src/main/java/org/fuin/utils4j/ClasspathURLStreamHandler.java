/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.utils4j;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Stream handler that reads from the class path.
 */
public final class ClasspathURLStreamHandler extends URLStreamHandler {

    private final ClassLoader cl;

    /**
     * Default constructor.
     */
    public ClasspathURLStreamHandler() {
        this.cl = getClass().getClassLoader();
    }

    /**
     * Constructor with class loader.
     * 
     * @param cl
     *            Class loader to use.
     */
    public ClasspathURLStreamHandler(final ClassLoader cl) {
        this.cl = cl;
    }

    @Override
    protected final URLConnection openConnection(final URL url) throws IOException {
        final String path = url.getPath();
        final URL resourceUrl = cl.getResource(path);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return resourceUrl.openConnection();
    }

}
