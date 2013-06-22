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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;

//CHECKSTYLE:OFF
public final class RandomAccessFileInputStreamTest {

    private static File file;

    private RandomAccessFileInputStream inputStream;

    /**
     * @testng.before-class
     */
    public final void beforeClass() throws IOException {
        final File dir = new File("src/test/resources/"
                + Utils4J.getPackagePath(RandomAccessFileInputStreamTest.class));
        file = new File(dir, "RandomAccessFileInputStreamData.bin");
        if (!file.exists()) {
            throw new IllegalStateException("File '" + file + "' not found!");
        }
    }

    /**
     * @testng.before-method
     */
    public final void beforeMethod() throws FileNotFoundException {
        inputStream = new RandomAccessFileInputStream(file, "r");
    }

    /**
     * @testng.after-method
     */
    public final void afterMethod() throws IOException {
        inputStream.close();
        inputStream = null;
    }

    /**
     * @testng.test
     */
    public final void testRead() throws IOException {
        for (int i = 0; i < 256; i++) {
            Assert.assertEquals(inputStream.read(), i);
        }
    }

    /**
     * @testng.test
     */
    public final void testReadBytes() throws IOException {
        final byte[] buf = new byte[128];
        inputStream.read(buf);
        for (int i = 0; i < 128; i++) {
            Assert.assertEquals(buf[i], i);
        }
    }

    /**
     * @testng.test
     */
    public final void testReadBytesOffsetLength() throws IOException {
        final byte[] buf = new byte[128];
        inputStream.read(buf, 100, 28);
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(buf[i], 0);
        }
        for (int i = 0; i < 28; i++) {
            Assert.assertEquals(buf[100 + i], i);
        }
    }

    /**
     * @testng.test
     */
    public final void testSkip() throws IOException {
        Assert.assertEquals(inputStream.read(), 0);
        inputStream.skip(1);
        Assert.assertEquals(inputStream.read(), 2);
        inputStream.skip(2);
        Assert.assertEquals(inputStream.read(), 5);
    }

    /**
     * @testng.test
     */
    public final void testFileSize() throws IOException {
        Assert.assertEquals(inputStream.fileSize(), 256);
    }

    /**
     * @testng.test
     */
    public final void testAvailable() throws IOException {
        for (int i = 256; i >= 0; i--) {
            Assert.assertEquals(inputStream.available(), i);
            inputStream.read();
        }
        Assert.assertEquals(inputStream.available(), 0);
    }

    /**
     * @testng.test
     */
    public final void testMarkSupported() {
        Assert.assertEquals(inputStream.markSupported(), true);
    }

    /**
     * @testng.test expectedExceptions="java.io.IOException"
     */
    public final void testResetWithoutMark() throws IOException {
        inputStream.reset();
    }

    /**
     * @testng.test
     */
    public final void testMarkAndReset() throws IOException {
        Assert.assertEquals(inputStream.read(), 0);
        Assert.assertEquals(inputStream.read(), 1);
        Assert.assertEquals(inputStream.read(), 2);
        inputStream.mark(Integer.MAX_VALUE);
        Assert.assertEquals(inputStream.read(), 3);
        Assert.assertEquals(inputStream.read(), 4);
        Assert.assertEquals(inputStream.read(), 5);
        inputStream.reset();
        Assert.assertEquals(inputStream.read(), 3);
        Assert.assertEquals(inputStream.read(), 4);
        Assert.assertEquals(inputStream.read(), 5);
    }

}
// CHECKSTYLE:ON
