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
package org.fuin.utils4j.fileprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

//CHECKSTYLE:OFF
public class FileProcessorTest {

    private static final File DIR = new File("src/test/java/org/fuin/utils4j/test");
    private static final File FILE = new File(DIR, "ClassWithPrivateConstructor.java");
    private static final File SUB = new File(DIR, "sub");
    private static final File SUB_FILE = new File(SUB, "OtherClass.java");

    /**
     * @testng.test
     */
    public final void testProcessFile() {

        // PREPARE
        final FileContainer processed = new FileContainer();
        final FileHandler handler = new FileHandler() {
            public FileHandlerResult handleFile(File file) {
                processed.file = file;
                return null;
            }
        };
        final FileProcessor testee = new FileProcessor(handler);

        // TEST
        testee.process(FILE);

        // VERIFY
        Assert.assertSame(processed.file, FILE);

    }

    /**
     * @testng.test
     */
    public final void testProcessDir() {

        // PREPARE
        final List processed = new ArrayList();
        final FileHandler handler = new FileHandler() {
            public FileHandlerResult handleFile(File file) {
                if (file.isFile()) {
                    processed.add(file);
                }
                return null;
            }
        };
        final FileProcessor testee = new FileProcessor(handler);

        // TEST
        testee.process(DIR);

        // VERIFY
        Assert.assertEquals(processed.size(), 2);
        if (processed.get(0).equals(FILE)) {
            Assert.assertEquals(processed.get(1), SUB_FILE);
        } else {
            Assert.assertEquals(processed.get(0), SUB_FILE);
            Assert.assertEquals(processed.get(1), FILE);
        }

    }

    /**
     * @testng.test
     */
    public final void testProcessDirSTOP() {

        // PREPARE
        final List processed = new ArrayList();
        final FileHandler handler = new FileHandler() {
            public FileHandlerResult handleFile(File file) {
                // STOP on first directory
                return FileHandlerResult.STOP;
            }
        };
        final FileProcessor testee = new FileProcessor(handler);

        // TEST
        testee.process(DIR);

        // VERIFY
        Assert.assertEquals(processed.size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testProcessDirSKIP_ALL() {

        // PREPARE
        final List processed = new ArrayList();
        final FileHandler handler = new FileHandler() {
            public FileHandlerResult handleFile(File file) {
                // SKIP_ALL on all directories
                return FileHandlerResult.SKIP_ALL;
            }
        };
        final FileProcessor testee = new FileProcessor(handler);

        // TEST
        testee.process(DIR);

        // VERIFY
        Assert.assertEquals(processed.size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testProcessDirSKIP_SUBDIRS() {

        // PREPARE
        final List processed = new ArrayList();
        final FileHandler handler = new FileHandler() {
            public FileHandlerResult handleFile(File file) {
                if (file.isFile()) {
                    processed.add(file);
                }
                return FileHandlerResult.SKIP_SUBDIRS;
            }
        };
        final FileProcessor testee = new FileProcessor(handler);

        // TEST
        testee.process(DIR);

        // VERIFY
        Assert.assertEquals(processed.size(), 1);
        Assert.assertEquals(processed.get(0), FILE);

    }

    /**
     * @testng.test
     */
    public final void testProcessDirSKIP_FILES() {

        // PREPARE
        final List processed = new ArrayList();
        final FileHandler handler = new FileHandler() {
            public FileHandlerResult handleFile(File file) {
                if (file.getName().equals("test")) {
                    return FileHandlerResult.SKIP_FILES;
                }
                if (file.isFile()) {
                    processed.add(file);
                }
                return FileHandlerResult.CONTINUE;

            }
        };
        final FileProcessor testee = new FileProcessor(handler);

        // TEST
        testee.process(DIR);

        // VERIFY
        Assert.assertEquals(processed.size(), 1);
        Assert.assertEquals(processed.get(0), SUB_FILE);

    }

    private static class FileContainer {
        public File file;
    }

}
// CHECKSTYLE:ON