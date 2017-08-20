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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//CHECKSTYLE:OFF
public class RandomAccessFileOutputStreamTest {

    private static File file;

    private static File inputFile;

    private RandomAccessFileOutputStream outputStream;

    @BeforeClass
    public static void beforeClass() throws IOException {
        final File dir = new File("src/test/resources/"
                + Utils4J.getPackagePath(RandomAccessFileOutputStream.class));
        file = new File(Utils4J.getTempDir(), "RandomAccessFileOutputStreamData.bin");
        inputFile = new File(dir, "RandomAccessFileInputStreamData.bin");
        if (!inputFile.exists()) {
            throw new IllegalStateException("File '" + inputFile + "' not found!");
        }
    }

    @Before
    public final void beforeMethod() throws IOException {
        file.delete();
    }

    @After
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

    @Test
    public final void testWriteByte() throws IOException {
        outputStream = createOutputStream();
        for (int i = 0; i < 256; i++) {
            outputStream.write(i);
        }
        outputStream.truncate();
        outputStream.close();
        // TODO Remove workaround when issue is fixed
        // https://github.com/joel-costigliola/assertj-core/issues/1059
        // assertThat(file).hasSameContentAs(inputFile);
        assertThat(FileUtils.contentEquals(file, inputFile)).isTrue();
    }

    @Test
    public final void testWriteByteArray() throws IOException {
        outputStream = createOutputStream();
        outputStream.write(create256bytes());
        outputStream.truncate();
        outputStream.close();
        // TODO Remove workaround when issue is fixed
        // https://github.com/joel-costigliola/assertj-core/issues/1059
        // assertThat(file).hasSameContentAs(inputFile);
        assertThat(FileUtils.contentEquals(file, inputFile)).isTrue();
    }

    @Test
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
            assertThat(in.read()).isEqualTo(254);
            assertThat(in.read()).isEqualTo(255);
            for (int i = 2; i < 256; i++) {
                assertThat(in.read()).isEqualTo(i);
            }
        } finally {
            in.close();
        }

    }

    @Test
    public final void testTruncate() throws IOException {

        // Create file filled with "0..255"
        create256bytesFile();

        // Open the file and write one byte
        outputStream = createOutputStream();
        outputStream.write(255);
        outputStream.truncate();
        outputStream.close();

        assertThat(file.length()).isEqualTo(1);

    }

    @Test
    public final void testCounter() throws IOException {

        // Open the file and write one byte
        outputStream = createOutputStream();

        assertThat(outputStream.getCounter()).isEqualTo(0);
        outputStream.write(255);
        assertThat(outputStream.getCounter()).isEqualTo(1);
        outputStream.write(255);
        assertThat(outputStream.getCounter()).isEqualTo(2);
        outputStream.write(new byte[] { 1, 2, 3 });
        assertThat(outputStream.getCounter()).isEqualTo(5);
        outputStream.write(new byte[] { 1, 2, 3 }, 0, 1);
        assertThat(outputStream.getCounter()).isEqualTo(6);

        outputStream.resetCounter();
        assertThat(outputStream.getCounter()).isEqualTo(0);
        outputStream.close();

    }

}
// CHECKSTYLE:ON
