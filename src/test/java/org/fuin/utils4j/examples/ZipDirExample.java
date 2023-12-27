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
package org.fuin.utils4j.examples;

import org.apache.commons.io.IOUtils;
import org.fuin.utils4j.Utils4J;

import java.io.*;

/**
 * Example for zipping a directory.
 */
public final class ZipDirExample {

    /**
     * Private constructor.
     */
    private ZipDirExample() {
        throw new UnsupportedOperationException("It's not allowed to create an instance of this class!");
    }

    private static void copyResourceToFile(final String resource, final File target) throws IOException {
        final InputStream in = ZipDirExample.class.getResourceAsStream(resource);
        try {
            final OutputStream out = new FileOutputStream(target);
            try {
                IOUtils.copy(in, out);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * Executes the example.
     * 
     * @param args
     *            Not used.
     * 
     * @throws IOException
     *             Error creating the archive.
     */
    public static void main(final String[] args) throws IOException {

        // Create a directory named 'mydir' in the tmp directory
        final File zipDir = new File(Utils4J.getTempDir(), "mydir");

        // Unpack example ZIP file
        final File zipFile = File.createTempFile("ZipDirExample", ".zip");
        try {
            copyResourceToFile("/myfile.zip", zipFile);
            zipDir.mkdir();
            Utils4J.unzip(zipFile, zipDir);
        } finally {
            zipFile.delete();
        }

        // Run the ZIP command
        Utils4J.zipDir(zipDir, "abc/def", new File(zipDir, "myfile1.zip"));
        // The archive will contain the following structure:
        // abc/def/x.txt
        // abc/def/y.txt
        // abc/def/z.txt

        // Run the ZIP command again
        Utils4J.zipDir(zipDir, "", new File(zipDir, "myfile2.zip"));
        // The second archive will contain the following structure:
        // x.txt
        // y.txt
        // z.txt

    }

}
