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
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.fuin.utils4j.MergeException.Problem;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//CHECKSTYLE:OFF
public class PropertiesFileTest {

    private static File dir;

    private static File testFile;

    private PropertiesFile propFile;

    @BeforeClass
    public static void beforeClass() throws IOException {
        testFile = new File(Utils4J.getTempDir(), "PropertiesFileTest.properties");
        dir = new File("src/test/resources/" + Utils4J.getPackagePath(PropertiesFileTest.class));
    }

    @Before
    public final void beforeMethod() throws IOException {
        // Create the initial content
        createPropertiesFile();
        // Create test file object
        propFile = new PropertiesFile(testFile);
    }

    @After
    public final void afterMethod() throws IOException {
        propFile = null;
    }

    private void createPropertiesFile() throws IOException {
        final String lf = System.getProperty("line.separator");
        final FileWriter fw = new FileWriter(testFile);
        try {
            fw.write("# Test" + lf);
            fw.write("A=1" + lf);
            fw.write("B=2" + lf);
            fw.write("C=3" + lf);
        } finally {
            fw.close();
        }
    }

    @Test
    public final void testConstructionFile() {
        final PropertiesFile propFile = new PropertiesFile(testFile);
        assertThat(propFile.getFile()).isEqualTo(testFile);
        assertThat(propFile.getEncoding()).isEqualTo("UTF-8");
        assertThat(propFile.isLoaded()).isFalse();
        assertThat(propFile.size()).isEqualTo(0);
        assertThat(propFile.getKeyList()).isEmpty();
        assertThat(propFile.getKeyArray()).isEmpty();
    }

    @Test
    public final void testConstructionFileEncoding() {
        final String encoding = "ISO-8859-1";
        final PropertiesFile propFile = new PropertiesFile(testFile, encoding);
        assertThat(propFile.getFile()).isEqualTo(testFile);
        assertThat(propFile.getEncoding()).isEqualTo(encoding);
        assertThat(propFile.isLoaded()).isFalse();
        assertThat(propFile.size()).isEqualTo(0);
        assertThat(propFile.getKeyList()).isEmpty();
        assertThat(propFile.getKeyArray()).isEmpty();
    }

    @Test
    public final void testPutAndGet() {
        final String key = "A";
        final String value = "1";
        propFile.put(key, value);
        assertThat(propFile.get(key)).isEqualTo(value);
        assertThat(propFile.get("a")).isNull();
    }

    @Test
    public final void testGetKeyArray() {
        propFile.put("A", "1");
        propFile.put("B", "2");
        propFile.put("C", "3");
        assertThat(propFile.getKeyArray()).isEqualTo(new String[] { "A", "B", "C" });
    }

    @Test
    public final void testGetKeyList() {
        propFile.put("A", "1");
        propFile.put("B", "2");
        propFile.put("C", "3");
        final List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        assertThat(propFile.getKeyList()).isEqualTo(list);
    }

    @Test
    public final void testKeyIterator() {
        propFile.put("A", "1");
        propFile.put("B", "2");
        propFile.put("C", "3");
        final Iterator<String> it = propFile.keyIterator();
        assertThat(it.next()).isEqualTo("A");
        assertThat(it.next()).isEqualTo("B");
        assertThat(it.next()).isEqualTo("C");
    }

    @Test
    public final void testLoad() throws IOException, LockingFailedException, MergeException {
        propFile.load();
        assertThat(propFile.get("A")).isEqualTo("1");
        assertThat(propFile.get("B")).isEqualTo("2");
        assertThat(propFile.get("C")).isEqualTo("3");
    }

    @Test
    public final void testToProperties() throws IOException, LockingFailedException, MergeException {
        propFile.load();
        final Properties props = propFile.toProperties();
        assertThat(props.get("A")).isEqualTo("1");
        assertThat(props.get("B")).isEqualTo("2");
        assertThat(props.get("C")).isEqualTo("3");
    }

    @Test
    public final void testSaveNoChange() throws IOException, MergeException, LockingFailedException {
        propFile.load();
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestA.properties"), testFile);
    }

    @Test
    public final void testSaveAddProperties() throws IOException, MergeException, LockingFailedException {
        propFile.load();
        propFile.put("D", "4");
        propFile.put("E", "5");
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestB.properties"), testFile);
    }

    @Test
    public final void testSaveRemoveProperties() throws IOException, MergeException, LockingFailedException {
        propFile.load();
        propFile.remove("A");
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestC.properties"), testFile);
    }

    @Test
    public final void testSaveChangeProperties() throws IOException, MergeException, LockingFailedException {
        propFile.load();
        propFile.put("A", "a");
        propFile.put("B", "b");
        propFile.put("C", "c");
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestD.properties"), testFile);
    }

    @Test
    public final void testConcurrentAdd() throws IOException, MergeException, LockingFailedException {

        // Load first one and modify
        final PropertiesFile propFile1 = new PropertiesFile(testFile);
        propFile1.load();
        propFile1.put("A", "a");
        propFile1.put("B", "b");
        propFile1.put("C", "c");

        // Load second one and add
        final PropertiesFile propFile2 = new PropertiesFile(testFile);
        propFile2.load();
        propFile2.put("A.1", "11");
        propFile2.put("B.1", "22");
        propFile2.put("C.1", "33");

        // Save first one
        propFile1.save("Test", true);

        // Save second one
        propFile2.save("Test", true);

        // Check result
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestE.properties"), testFile);

    }

    @Test
    public final void testMergeError() throws IOException, MergeException, LockingFailedException {

        // Load first one
        final PropertiesFile propFile1 = new PropertiesFile(testFile);
        propFile1.load();

        // Load second one
        final PropertiesFile propFile2 = new PropertiesFile(testFile);
        propFile2.load();

        // Modify the same property concurrently
        propFile1.put("A", "a");
        propFile2.put("A", "x");

        // Save first one
        propFile1.save("Test", true);

        // Save second one
        try {
            propFile2.save("Test", true);
            System.out.println("------");
            fail("Expected " + MergeException.class.getName() + " [" + testFile + "]");
        } catch (final MergeException ex) {
            final Problem[] problems = ex.getProblems();
            assertThat(problems).isNotNull();
            assertThat(problems.length).isEqualTo(1);
            final Problem p = problems[0];
            assertThat(p.getProp().getKey()).isEqualTo("A");
            assertThat(p.getProp().getValue()).isEqualTo("x");
            assertThat(p.getFileProp().getValue()).isEqualTo("a");
        }

    }

}
// CHECKSTYLE:ON
