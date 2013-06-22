/**
 * Copyright (C) 2009 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.utils4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

//CHECKSTYLE:OFF
public class RandomAccessFileOutputStreamTest {

    private static File file;

    private static File inputFile;

    private RandomAccessFileOutputStream outputStream;

    /**
     * @testng.before-class
     */
    public final void beforeClass() throws IOException {
        final File dir = new File("src/test/resources/"
                + Utils4J.getPackagePath(RandomAccessFileOutputStream.class));
        file = new File(Utils4J.getTempDir(), "RandomAccessFileOutputStreamData.bin");
        inputFile = new File(dir, "RandomAccessFileInputStreamData.bin");
        if (!inputFile.exists()) {
            throw new IllegalStateException("File '" + inputFile + "' not found!");
        }
    }

    /**
     * @testng.before-method
     */
    public final void beforeMethod() throws IOException {
        file.delete();
    }

    /**
     * @testng.after-method
     */
    public final void afterMethod() throws IOException {
        outputStream.close();
        outputStream = null;
    }

    private final RandomAccessFileOutputStream createOutputStream() throws IOException {
        return new RandomAccessFileOutputStream(file, "rw");
    }

    private final byte[] create256bytes() {
        final byte[] buf = new byte[256];
        for (int i = 0; i < 256; i++) {
            buf[i] = (byte) i;
        }
        return buf;
    }

    private final void create256bytesFile() throws IOException {
        final FileOutputStream out = new FileOutputStream(file);
        try {
            out.write(create256bytes());
        } finally {
            out.close();
        }
    }

    /**
     * @testng.test
     */
    public final void testWriteByte() throws IOException {
        outputStream = createOutputStream();
        for (int i = 0; i < 256; i++) {
            outputStream.write(i);
        }
        outputStream.truncate();
        outputStream.close();
        Assert.assertTrue(FileUtils.contentEquals(inputFile, file));
    }

    /**
     * @testng.test
     */
    public final void testWriteByteArray() throws IOException {
        outputStream = createOutputStream();
        outputStream.write(create256bytes());
        outputStream.truncate();
        outputStream.close();
        Assert.assertTrue(FileUtils.contentEquals(inputFile, file));
    }

    /**
     * @testng.test
     */
    public final void testWriteByteArrayOffsetLength() throws IOException {

        // Create file filled with "0..255"
        create256bytesFile();

        // Open the file
        outputStream = createOutputStream();
        // Use only the bytes 1+2 from the array
        final byte[] buf = new byte[] { (byte) 254, (byte) 255, (byte) 255, (byte) 255 };
        outputStream.write(buf, 0, 2);
        for (int i = 2; i < 256; i++) {
            outputStream.write(i);
        }
        outputStream.close();

        // Read again and check if above content was written
        final FileInputStream in = new FileInputStream(file);
        try {
            Assert.assertEquals(in.read(), 254);
            Assert.assertEquals(in.read(), 255);
            for (int i = 2; i < 256; i++) {
                Assert.assertEquals(in.read(), i);
            }
        } finally {
            in.close();
        }

    }

    /**
     * @testng.test
     */
    public final void testTruncate() throws IOException {

        // Create file filled with "0..255"
        create256bytesFile();

        // Open the file and write one byte
        outputStream = createOutputStream();
        outputStream.write(255);
        outputStream.truncate();
        outputStream.close();

        Assert.assertEquals(file.length(), 1);

    }

    /**
     * @testng.test
     */
    public final void testCounter() throws IOException {

        // Open the file and write one byte
        outputStream = createOutputStream();

        Assert.assertEquals(outputStream.getCounter(), 0);
        outputStream.write(255);
        Assert.assertEquals(outputStream.getCounter(), 1);
        outputStream.write(255);
        Assert.assertEquals(outputStream.getCounter(), 2);
        outputStream.write(new byte[] { 1, 2, 3 });
        Assert.assertEquals(outputStream.getCounter(), 5);
        outputStream.write(new byte[] { 1, 2, 3 }, 0, 1);
        Assert.assertEquals(outputStream.getCounter(), 6);

        outputStream.resetCounter();
        Assert.assertEquals(outputStream.getCounter(), 0);
        outputStream.close();

    }

}
// CHECKSTYLE:ON
