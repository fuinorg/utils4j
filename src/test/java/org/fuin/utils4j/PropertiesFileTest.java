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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.fuin.utils4j.MergeException.Problem;
import org.testng.Assert;

//CHECKSTYLE:OFF
public class PropertiesFileTest {

    private static File dir;

    private static File testFile;

    private PropertiesFile propFile;

    /**
     * @testng.before-class
     */
    public final void beforeClass() throws IOException {
        testFile = new File(Utils4J.getTempDir(), "PropertiesFileTest.properties");
        dir = new File("src/test/resources/" + Utils4J.getPackagePath(PropertiesFileTest.class));
    }

    /**
     * @testng.before-method
     */
    public final void beforeMethod() throws IOException {
        // Create the initial content
        createPropertiesFile();
        // Create test file object
        propFile = new PropertiesFile(testFile);
    }

    /**
     * @testng.after-method
     */
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

    /**
     * @testng.test
     */
    public final void testConstructionFile() {
        final PropertiesFile propFile = new PropertiesFile(testFile);
        Assert.assertEquals(propFile.getFile(), testFile);
        Assert.assertEquals(propFile.getEncoding(), "UTF-8");
        Assert.assertFalse(propFile.isLoaded());
        Assert.assertEquals(propFile.size(), 0);
        Assert.assertEquals(propFile.getKeyList(), new ArrayList());
        Assert.assertEquals(propFile.getKeyArray(), new String[] {});
    }

    /**
     * @testng.test
     */
    public final void testConstructionFileEncoding() {
        final String encoding = "ISO-8859-1";
        final PropertiesFile propFile = new PropertiesFile(testFile, encoding);
        Assert.assertEquals(propFile.getFile(), testFile);
        Assert.assertEquals(propFile.getEncoding(), encoding);
        Assert.assertFalse(propFile.isLoaded());
        Assert.assertEquals(propFile.size(), 0);
        Assert.assertEquals(propFile.getKeyList(), new ArrayList());
        Assert.assertEquals(propFile.getKeyArray(), new String[] {});
    }

    /**
     * @testng.test
     */
    public final void testPutAndGet() {
        final String key = "A";
        final String value = "1";
        propFile.put(key, value);
        Assert.assertEquals(propFile.get(key), value);
        Assert.assertNull(propFile.get("a"));
    }

    /**
     * @testng.test
     */
    public final void testGetKeyArray() {
        propFile.put("A", "1");
        propFile.put("B", "2");
        propFile.put("C", "3");
        Assert.assertEquals(propFile.getKeyArray(), new String[] { "A", "B", "C" });
    }

    /**
     * @testng.test
     */
    public final void testGetKeyList() {
        propFile.put("A", "1");
        propFile.put("B", "2");
        propFile.put("C", "3");
        final List list = new ArrayList();
        list.add("A");
        list.add("B");
        list.add("C");
        Assert.assertEquals(propFile.getKeyList(), list);
    }

    /**
     * @testng.test
     */
    public final void testKeyIterator() {
        propFile.put("A", "1");
        propFile.put("B", "2");
        propFile.put("C", "3");
        final Iterator it = propFile.keyIterator();
        Assert.assertEquals(it.next(), "A");
        Assert.assertEquals(it.next(), "B");
        Assert.assertEquals(it.next(), "C");
    }

    /**
     * @testng.test
     */
    public final void testLoad() throws IOException, LockingFailedException, MergeException {
        propFile.load();
        Assert.assertEquals(propFile.get("A"), "1");
        Assert.assertEquals(propFile.get("B"), "2");
        Assert.assertEquals(propFile.get("C"), "3");
    }

    /**
     * @testng.test
     */
    public final void testToProperties() throws IOException, LockingFailedException, MergeException {
        propFile.load();
        final Properties props = propFile.toProperties();
        Assert.assertEquals(props.get("A"), "1");
        Assert.assertEquals(props.get("B"), "2");
        Assert.assertEquals(props.get("C"), "3");
    }

    /**
     * @testng.test
     */
    public final void testSaveNoChange() throws IOException, MergeException, LockingFailedException {
        propFile.load();
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestA.properties"), testFile);
    }

    /**
     * @testng.test
     */
    public final void testSaveAddProperties() throws IOException, MergeException,
            LockingFailedException {
        propFile.load();
        propFile.put("D", "4");
        propFile.put("E", "5");
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestB.properties"), testFile);
    }

    /**
     * @testng.test
     */
    public final void testSaveRemoveProperties() throws IOException, MergeException,
            LockingFailedException {
        propFile.load();
        propFile.remove("A");
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestC.properties"), testFile);
    }

    /**
     * @testng.test
     */
    public final void testSaveChangeProperties() throws IOException, MergeException,
            LockingFailedException {
        propFile.load();
        propFile.put("A", "a");
        propFile.put("B", "b");
        propFile.put("C", "c");
        propFile.save("Test", true);
        TestHelper.assertPropertiesEqual(new File(dir, "PropertiesFileTestD.properties"), testFile);
    }

    /**
     * @testng.test
     */
    public final void testConcurrentAdd() throws IOException, MergeException,
            LockingFailedException {

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

    /**
     * @testng.test
     */
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
            Assert.fail("Expected " + MergeException.class.getName() + " [" + testFile + "]");
        } catch (final MergeException ex) {
            final Problem[] problems = ex.getProblems();
            Assert.assertNotNull(problems);
            Assert.assertEquals(problems.length, 1);
            final Problem p = problems[0];
            Assert.assertEquals(p.getProp().getKey(), "A");
            Assert.assertEquals(p.getProp().getValue(), "x");
            Assert.assertEquals(p.getFileProp().getValue(), "a");
        }

    }

}
// CHECKSTYLE:ON
