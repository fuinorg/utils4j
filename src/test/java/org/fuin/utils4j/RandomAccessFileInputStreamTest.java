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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//CHECKSTYLE:OFF
public final class RandomAccessFileInputStreamTest {

    private static File file;

    private RandomAccessFileInputStream inputStream;

    @BeforeClass
    public static void beforeClass() throws IOException {
        final File dir = new File("src/test/resources/" + Utils4J.getPackagePath(RandomAccessFileInputStreamTest.class));
        file = new File(dir, "RandomAccessFileInputStreamData.bin");
        if (!file.exists()) {
            throw new IllegalStateException("File '" + file + "' not found!");
        }
    }

    @Before
    public final void beforeMethod() throws FileNotFoundException {
        inputStream = new RandomAccessFileInputStream(file, "r");
    }

    @After
    public final void afterMethod() throws IOException {
        inputStream.close();
        inputStream = null;
    }

    @Test
    public final void testRead() throws IOException {
        for (int i = 0; i < 256; i++) {
            assertThat(inputStream.read()).isEqualTo(i);
        }
    }

    @Test
    public final void testReadBytes() throws IOException {
        final byte[] buf = new byte[128];
        inputStream.read(buf);
        for (int i = 0; i < 128; i++) {
            assertThat(buf[i]).isEqualTo((byte) i);
        }
    }

    @Test
    public final void testReadBytesOffsetLength() throws IOException {
        final byte[] buf = new byte[128];
        inputStream.read(buf, 100, 28);
        for (int i = 0; i < 100; i++) {
            assertThat(buf[i]).isEqualTo((byte) 0);
        }
        for (int i = 0; i < 28; i++) {
            assertThat(buf[100 + i]).isEqualTo((byte) i);
        }
    }

    @Test
    public final void testSkip() throws IOException {
        assertThat(inputStream.read()).isEqualTo(0);
        inputStream.skip(1);
        assertThat(inputStream.read()).isEqualTo(2);
        inputStream.skip(2);
        assertThat(inputStream.read()).isEqualTo(5);
    }

    @Test
    public final void testFileSize() throws IOException {
        assertThat(inputStream.fileSize()).isEqualTo(256);
    }

    @Test
    public final void testAvailable() throws IOException {
        for (int i = 256; i >= 0; i--) {
            assertThat(inputStream.available()).isEqualTo(i);
            inputStream.read();
        }
        assertThat(inputStream.available()).isEqualTo(0);
    }

    @Test
    public final void testMarkSupported() {
        assertThat(inputStream.markSupported()).isTrue();
    }

    @Test(expected = IOException.class)
    public final void testResetWithoutMark() throws IOException {
        inputStream.reset();
    }

    @Test
    public final void testMarkAndReset() throws IOException {
        assertThat(inputStream.read()).isEqualTo(0);
        assertThat(inputStream.read()).isEqualTo(1);
        assertThat(inputStream.read()).isEqualTo(2);
        inputStream.mark(Integer.MAX_VALUE);
        assertThat(inputStream.read()).isEqualTo(3);
        assertThat(inputStream.read()).isEqualTo(4);
        assertThat(inputStream.read()).isEqualTo(5);
        inputStream.reset();
        assertThat(inputStream.read()).isEqualTo(3);
        assertThat(inputStream.read()).isEqualTo(4);
        assertThat(inputStream.read()).isEqualTo(5);
    }

}
// CHECKSTYLE:ON
