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

import static org.fuin.utils4j.Utils4J.checkNotNull;
import static org.fuin.utils4j.Utils4J.checkValidFile;
import static org.fuin.utils4j.Utils4J.createUrl;
import static org.fuin.utils4j.Utils4J.getPackagePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Utilities related to the {@link Properties} class.
 */
public final class PropertiesUtils {

    /**
     * Private default constructor.
     */
    private PropertiesUtils() {
        throw new UnsupportedOperationException("This utility class is not intended to be instanciated!");
    }

    /**
     * Load properties from classpath.
     * 
     * @param clasz
     *            Class in the same package as the properties file - Cannot be <code>null</code>.
     * @param filename
     *            Name of the properties file (without path) - Cannot be <code>null</code>.
     * 
     * @return Properties.
     */
    public static Properties loadProperties(final Class<?> clasz, final String filename) {
        checkNotNull("clasz", clasz);
        checkNotNull("filename", filename);

        final String path = getPackagePath(clasz);
        final String resPath = path + "/" + filename;
        return loadProperties(clasz.getClassLoader(), resPath);

    }

    /**
     * Loads a resource from the classpath as properties.
     * 
     * @param loader
     *            Class loader to use.
     * @param resource
     *            Resource to load.
     * 
     * @return Properties.
     */
    public static Properties loadProperties(final ClassLoader loader, final String resource) {
        checkNotNull("loader", loader);
        checkNotNull("resource", resource);

        final Properties props = new Properties();
        try (final InputStream inStream = loader.getResourceAsStream(resource)) {
            if (inStream == null) {
                throw new IllegalArgumentException("Resource '" + resource + "' not found!");
            }
            props.load(inStream);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
        return props;
    }

    /**
     * Load properties from a file.
     * 
     * @param file
     *            Properties file - Cannot be <code>null</code> and must be a valid file.
     * 
     * @return Properties.
     */
    public static Properties loadProperties(final File file) {
        checkNotNull("file", file);
        checkValidFile(file);
        final Properties props = new Properties();
        try (final InputStream inStream = new FileInputStream(file)) {
            props.load(inStream);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
        return props;
    }

    /**
     * Save properties to a file.
     * 
     * @param file
     *            Destination file - Cannot be <code>null</code> and parent directory must exist.
     * @param props
     *            Properties to save - Cannot be <code>null</code>.
     * @param comment
     *            Comment for the file.
     */
    public static void saveProperties(final File file, final Properties props, final String comment) {
        checkNotNull("file", file);
        checkNotNull("props", props);

        if (!file.getParentFile().exists()) {
            throw new IllegalArgumentException("The parent directory '" + file.getParentFile()
                    + "' does not exist [file='" + file + "']!");
        }
        try (final OutputStream outStream = new FileOutputStream(file)) {
            props.store(outStream, comment);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Load a file from an directory.
     * 
     * @param baseUrl
     *            Directory URL - Cannot be <code>null</code>.
     * @param filename
     *            Filename without path - Cannot be <code>null</code>.
     * 
     * @return Properties.
     */
    public static Properties loadProperties(final URL baseUrl, final String filename) {
        return loadProperties(createUrl(baseUrl, "", filename));
    }

    /**
     * Load a file from an URL.
     * 
     * @param fileURL
     *            Property file URL - Cannot be <code>null</code>.
     * 
     * @return Properties.
     */
    public static Properties loadProperties(final URL fileURL) {
        checkNotNull("fileURL", fileURL);
        final Properties props = new Properties();
        try (final InputStream inStream = fileURL.openStream()) {
            props.load(inStream);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
        return props;
    }

    /**
     * Load a file from an directory. Wraps a possible <code>MalformedURLException</code> exception into a
     * <code>RuntimeException</code>.
     * 
     * @param baseUrl
     *            Directory URL as <code>String</code> - Cannot be <code>null</code>.
     * @param filename
     *            Filename without path - Cannot be <code>null</code>.
     * 
     * @return Properties.
     */
    public static Properties loadProperties(final String baseUrl, final String filename) {
        checkNotNull("baseUrl", baseUrl);
        checkNotNull("filename", filename);

        try {
            final URL url = new URL(baseUrl);
            return loadProperties(url, filename);
        } catch (final MalformedURLException ex) {
            throw new IllegalArgumentException("The argument 'srcUrl' is not a valid URL [" + baseUrl + "]!",
                    ex);
        }

    }

}
