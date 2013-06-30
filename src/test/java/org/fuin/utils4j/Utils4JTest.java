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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.fuin.utils4j.test.ClassWithPrivateConstructor;

/**
 * Tests for Utils4J.
 */
// CHECKSTYLE:OFF
public class Utils4JTest {

    private static final File TEST_PROPERTIES_FILE = new File(
            "src/test/resources/org/fuin/utils4j/test.properties");

    private static final File ZIP_FILE = new File("src/test/resources/test.zip");

    private static final Map vars = new HashMap();

    /**
     * @testng.before-class
     */
    public final void beforeClass() {
        vars.put("one", "1");
        vars.put("two", "2");
        vars.put("3", "three");
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsNull() {
        Assert.assertEquals(null, Utils4J.replaceVars(null, vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsEmpty() {
        Assert.assertEquals("", Utils4J.replaceVars("", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsNoVars() {
        Assert.assertEquals("one two three", Utils4J.replaceVars("one two three", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsSingleVar1() {
        Assert.assertEquals("1", Utils4J.replaceVars("${one}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsSingleVar2() {
        Assert.assertEquals(" 1", Utils4J.replaceVars(" ${one}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsSingleVar3() {
        Assert.assertEquals("1 ", Utils4J.replaceVars("${one} ", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsSingleVar4() {
        Assert.assertEquals(" 1 ", Utils4J.replaceVars(" ${one} ", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsMultipleVars1() {
        Assert.assertEquals(" 1 2 three ", Utils4J.replaceVars(" ${one} ${two} ${3} ", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsMultipleVars2() {
        Assert.assertEquals("1 2 three ", Utils4J.replaceVars("${one} ${two} ${3} ", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsMultipleVars3() {
        Assert.assertEquals("12three", Utils4J.replaceVars("${one}${two}${3}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsMultipleVars4() {
        Assert.assertEquals(" 1 2 three", Utils4J.replaceVars(" ${one} ${two} ${3}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsUnknown1() {
        Assert.assertEquals("${xyz}", Utils4J.replaceVars("${xyz}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsUnknown2() {
        Assert.assertEquals("1${xyz}", Utils4J.replaceVars("${one}${xyz}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsUnknown3() {
        Assert.assertEquals("${xyz}2", Utils4J.replaceVars("${xyz}${two}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsUnknown4() {
        Assert.assertEquals("1${xyz}2", Utils4J.replaceVars("${one}${xyz}${two}", vars));
    }

    /**
     * @testng.test
     */
    public final void testReplaceVarsNoClosingBracket() {
        Assert.assertEquals("1${two", Utils4J.replaceVars("${one}${two", vars));
    }

    /**
     * @testng.test
     */
    public final void testGetPackagePath() {
        Assert.assertEquals("org/fuin/utils4j", Utils4J.getPackagePath(Utils4J.class));
    }

    /**
     * @testng.test
     */
    public final void testGetResource() throws IOException {
        final URL url = new File(new File(".").getCanonicalPath(),
                "target/test-classes/org/fuin/utils4j/test.properties").toURL();
        Assert.assertEquals(url, Utils4J.getResource(Utils4JTest.class, "test.properties"));
    }

    /**
     * @testng.test
     */
    public final void testLoadPropertiesClassString() {
        final Properties props = Utils4J.loadProperties(Utils4JTest.class, "test.properties");
        Assert.assertEquals(3, props.size());
        Assert.assertEquals("1", props.get("one"));
        Assert.assertEquals("2", props.get("two"));
        Assert.assertEquals("3", props.get("three"));
    }

    /**
     * @testng.test
     */
    public final void testLoadPropertiesFile() throws IOException {
        final Properties props = Utils4J.loadProperties(TEST_PROPERTIES_FILE);
        Assert.assertEquals(3, props.size());
        Assert.assertEquals("1", props.get("one"));
        Assert.assertEquals("2", props.get("two"));
        Assert.assertEquals("3", props.get("three"));
    }

    /**
     * @testng.test
     */
    public final void testLoadPropertiesURLString() throws MalformedURLException {
        final Properties props = Utils4J.loadProperties(TEST_PROPERTIES_FILE.getParentFile()
                .toURI().toURL(), TEST_PROPERTIES_FILE.getName());
        Assert.assertEquals(3, props.size());
        Assert.assertEquals("1", props.get("one"));
        Assert.assertEquals("2", props.get("two"));
        Assert.assertEquals("3", props.get("three"));
    }

    /**
     * @testng.test
     */
    public final void testLoadPropertiesURL() throws MalformedURLException {
        final Properties props = Utils4J.loadProperties(TEST_PROPERTIES_FILE.toURI().toURL());
        Assert.assertEquals(3, props.size());
        Assert.assertEquals("1", props.get("one"));
        Assert.assertEquals("2", props.get("two"));
        Assert.assertEquals("3", props.get("three"));
    }

    /**
     * @testng.test
     */
    public final void testLoadPropertiesStringString() throws MalformedURLException {
        final Properties props = Utils4J.loadProperties(TEST_PROPERTIES_FILE.getParentFile()
                .toURI().toURL().toExternalForm(), TEST_PROPERTIES_FILE.getName());
        Assert.assertEquals(3, props.size());
        Assert.assertEquals("1", props.get("one"));
        Assert.assertEquals("2", props.get("two"));
        Assert.assertEquals("3", props.get("three"));
    }

    /**
     * @testng.test
     */
    public final void testCheckValidFileOK() {
        Utils4J.checkValidFile(TEST_PROPERTIES_FILE);
    }

    /**
     * @testng.test
     */
    public final void testCheckValidFileNotExisting() {
        try {
            Utils4J.checkValidFile(new File(TEST_PROPERTIES_FILE.getParentFile(), "foobar.txt"));
            Assert.fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testCheckValidFileDirectory() {
        try {
            Utils4J.checkValidFile(TEST_PROPERTIES_FILE.getParentFile());
            Assert.fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testCheckValidDirOK() {
        Utils4J.checkValidDir(TEST_PROPERTIES_FILE.getParentFile());
    }

    /**
     * @testng.test
     */
    public final void testCheckValidDirNotExisting() {
        try {
            Utils4J.checkValidDir(new File(TEST_PROPERTIES_FILE.getParentFile(), "foobar"));
            Assert.fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testCheckValidDirFile() {
        try {
            Utils4J.checkValidDir(TEST_PROPERTIES_FILE);
            Assert.fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testSaveProperties() throws IOException {
        final File testFile = File.createTempFile("Utils4JTest-", ".properties");
        try {
            // Write to file
            Properties props = new Properties();
            props.put("eins", "1");
            props.put("zwei", "2");
            props.put("drei", "3");
            Utils4J.saveProperties(testFile, props, "COMMENT");

            // Read it
            props = Utils4J.loadProperties(testFile);
            Assert.assertEquals(3, props.size());
            Assert.assertEquals("1", props.get("eins"));
            Assert.assertEquals("2", props.get("zwei"));
            Assert.assertEquals("3", props.get("drei"));

        } finally {
            testFile.delete();
        }
    }

    /**
     * @testng.test
     */
    public final void testCreateInstanceOK() {
        final Object obj = Utils4J.createInstance(Utils4JTest.class.getName());
        Assert.assertTrue(obj instanceof Utils4JTest);
    }

    /**
     * @testng.test
     */
    public final void testCreateInstanceClassNotFound() {
        try {
            Utils4J.createInstance("x.y.Z");
            Assert.fail();
        } catch (final RuntimeException ex) {
            Assert.assertTrue(ex.getCause() instanceof ClassNotFoundException);
        }
    }

    /**
     * @testng.test
     */
    public final void testCreateInstanceInstantiationProblem() {
        try {
            Utils4J.createInstance(Cancelable.class.getName());
            Assert.fail();
        } catch (final RuntimeException ex) {
            Assert.assertTrue(ex.getCause() instanceof InstantiationException);
        }
    }

    /**
     * @testng.test
     */
    public final void testCreateInstanceIllegalAccess() {
        try {
            Utils4J.createInstance(ClassWithPrivateConstructor.class.getName());
            Assert.fail();
        } catch (final RuntimeException ex) {
            Assert.assertTrue(ex.getCause() instanceof IllegalAccessException);
        }
    }

    /**
     * @testng.test
     */
    public final void testContainsURL() throws IOException {
        final URL[] urls = new URL[] { new URL("http://www.google.com"),
                new URL("http://www.yahoo.com"), new URL("file:/foobar.txt") };
        Assert.assertTrue(Utils4J.containsURL(urls, new URL("http://www.google.com")));
        Assert.assertFalse(Utils4J.containsURL(urls, new URL("http://www.google.com/")));
        Assert.assertFalse(Utils4J.containsURL(urls, new URL("http://www.abc.com")));
    }

    /**
     * @testng.test
     */
    public final void testCreateHash() {
        Assert.assertEquals("e13c4b796b94a61b9d0050941676d129",
                Utils4J.createHashMD5(TEST_PROPERTIES_FILE));
    }

    /**
     * @testng.test
     */
    public final void testCreateUrlDirFile() throws IOException {
        Assert.assertEquals(new URL("http://www.fuin.org/test/index.html"),
                Utils4J.createUrl(new URL("http://www.fuin.org"), "test", "index.html"));
        Assert.assertEquals(new URL("http://www.fuin.org/test/index.html"),
                Utils4J.createUrl(new URL("http://www.fuin.org/"), "test", "index.html"));
    }

    /**
     * @testng.test
     */
    public final void testCreateUrlNullDirFile() throws IOException {
        Assert.assertEquals(new URL("http://www.fuin.org/index.html"),
                Utils4J.createUrl(new URL("http://www.fuin.org"), null, "index.html"));
        Assert.assertEquals(new URL("http://www.fuin.org/index.html"),
                Utils4J.createUrl(new URL("http://www.fuin.org/"), null, "index.html"));
    }

    /**
     * @testng.test
     */
    public final void testCreateUrlEmptyDirFile() throws IOException {
        Assert.assertEquals(new URL("http://www.fuin.org/index.html"),
                Utils4J.createUrl(new URL("http://www.fuin.org"), "", "index.html"));
        Assert.assertEquals(new URL("http://www.fuin.org/index.html"),
                Utils4J.createUrl(new URL("http://www.fuin.org/"), "", "index.html"));
    }

    /**
     * @testng.test
     */
    public final void testGetRelativePathOK() {
        Assert.assertEquals(
                "org" + File.separator + "fuin" + File.separator + "utils4j",
                Utils4J.getRelativePath(TEST_PROPERTIES_FILE.getParentFile().getParentFile()
                        .getParentFile().getParentFile(), TEST_PROPERTIES_FILE.getParentFile()));
    }
    
    /**
     * @testng.test
     */
    public final void testGetBackToRootPath() {
		Assert.assertEquals("[1]", "../../..", Utils4J.getBackToRootPath("a/b/c", '/'));
        Assert.assertEquals("[2]", "../../", Utils4J.getBackToRootPath("a/b/", '/'));
        Assert.assertEquals("[3]", "..", Utils4J.getBackToRootPath("a", '/'));
        Assert.assertEquals("[4]", "", Utils4J.getBackToRootPath("", '/'));
    }

    /**
     * @testng.test
     */
    public final void testGetRelativePathSame() {
        Assert.assertEquals(
                "",
                Utils4J.getRelativePath(TEST_PROPERTIES_FILE.getParentFile(),
                        TEST_PROPERTIES_FILE.getParentFile()));
    }

    /**
     * @testng.test
     */
    public final void testGetRelativePathNotInsideBaseDir() {
        try {
            Utils4J.getRelativePath(TEST_PROPERTIES_FILE.getParentFile(), TEST_PROPERTIES_FILE
                    .getParentFile().getParentFile());
            Assert.fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testAddToClasspathString() throws MalformedURLException {
        final ClassLoader classLoader = Utils4J.class.getClassLoader();
        if (!(classLoader instanceof URLClassLoader)) {
            throw new IllegalStateException("Classloader is not an URL classloader! ["
                    + classLoader.getClass().getName() + "]");
        }
        final URLClassLoader urlClassLoader = (URLClassLoader) classLoader;

        final URL url = new URL("file:/test1.jar");
        Assert.assertFalse(Utils4J.containsURL(urlClassLoader.getURLs(), url));
        Utils4J.addToClasspath(url.toExternalForm());
        Assert.assertTrue(Utils4J.containsURL(urlClassLoader.getURLs(), url));

    }

    /**
     * @testng.test
     */
    public final void testAddToClasspathURL() throws MalformedURLException {
        final ClassLoader classLoader = Utils4J.class.getClassLoader();
        if (!(classLoader instanceof URLClassLoader)) {
            throw new IllegalStateException("Classloader is not an URL classloader! ["
                    + classLoader.getClass().getName() + "]");
        }
        final URLClassLoader urlClassLoader = (URLClassLoader) classLoader;

        final URL url = new URL("file:/test2.jar");
        Assert.assertFalse(Utils4J.containsURL(urlClassLoader.getURLs(), url));
        Utils4J.addToClasspath(url);
        Assert.assertTrue(Utils4J.containsURL(urlClassLoader.getURLs(), url));

    }

    /**
     * @testng.test
     */
    public final void testCheckNotNullOK() {
        Utils4J.checkNotNull("name", "123");
    }

    /**
     * @testng.test
     */
    public final void testCheckNotNullFail() {
        try {
            Utils4J.checkNotNull("name", null);
            Assert.fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testCheckNotEmptyOK() {
        Utils4J.checkNotEmpty("name", "abc");
    }

    /**
     * @testng.test
     */
    public final void testCheckNotEmptyFail() {
        try {
            Utils4J.checkNotEmpty("name", "");
            Assert.fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testInvokeOK() throws InvokeMethodFailedException {
        Assert.assertEquals("abc", Utils4J.invoke(new IllegalNullArgumentException("abc"),
                "getArgument", new Class[] {}, new Object[] {}));
    }

    /**
     * @testng.test
     */
    public final void testInvokeFail() throws InvokeMethodFailedException {
        try {
            Utils4J.invoke(new IllegalNullArgumentException("abc"), "getArgument",
                    new Class[] { String.class }, new Object[] { "" });
            Assert.fail();
        } catch (final InvokeMethodFailedException ex) {
            // OK
        }
    }

    /**
     * @testng.test
     */
    public final void testUnzip() throws IOException {
        final File tmpDir = Utils4J.getTempDir();
        final File readMeTxt = new File(tmpDir, "readme.txt");
        final File dirOne = new File(tmpDir, "one");
        final File dirTwo = new File(tmpDir, "two");
        final File dirThree = new File(tmpDir, "three");
        final File fileOne = new File(dirOne, "1.txt");
        final File fileTwo = new File(dirTwo, "2.txt");
        final File fileThree = new File(dirThree, "3.txt");

        // Preconditions
        Assert.assertFalse(readMeTxt.exists());
        Assert.assertFalse(dirOne.exists());
        Assert.assertFalse(dirTwo.exists());
        Assert.assertFalse(dirThree.exists());
        Assert.assertFalse(fileOne.exists());
        Assert.assertFalse(fileTwo.exists());
        Assert.assertFalse(fileThree.exists());

        // Execute method
        Utils4J.unzip(ZIP_FILE, tmpDir);

        try {

            // Postconditions
            Assert.assertTrue(readMeTxt.exists());
            Assert.assertTrue(dirOne.exists());
            Assert.assertTrue(dirOne.isDirectory());
            Assert.assertTrue(dirTwo.exists());
            Assert.assertTrue(dirTwo.isDirectory());
            Assert.assertTrue(dirThree.exists());
            Assert.assertTrue(dirThree.isDirectory());
            Assert.assertTrue(fileOne.exists());
            Assert.assertTrue(fileTwo.exists());
            Assert.assertTrue(fileThree.exists());

        } finally {
            // Cleanup
            fileOne.delete();
            fileTwo.delete();
            fileThree.delete();
            dirOne.delete();
            dirTwo.delete();
            dirThree.delete();
            readMeTxt.delete();
        }

    }

    /**
     * @testng.test
     */
    public final void testZip() throws IOException {

        final File tmpDir = Utils4J.getTempDir();
        final File tmpSubDir = new File(tmpDir, "test");
        if (tmpSubDir.exists()) {
            FileUtils.deleteDirectory(tmpSubDir);
        }
        Assert.assertTrue(tmpSubDir.mkdir());

        final File destFile = new File(tmpDir, "test2.zip");
        final File readMeTxt = new File(tmpSubDir, "readme.txt");
        final File dirOne = new File(tmpSubDir, "one");
        final File dirTwo = new File(tmpSubDir, "two");
        final File dirThree = new File(tmpSubDir, "three");
        final File fileOne = new File(dirOne, "1.txt");
        final File fileTwo = new File(dirTwo, "2.txt");
        final File fileThree = new File(dirThree, "3.txt");

        final String destPath = "abc" + File.separator + "def";
        final File destSubDir = new File(tmpDir, destPath);
        if (destSubDir.exists()) {
            FileUtils.deleteDirectory(destSubDir);
        }

        Utils4J.unzip(ZIP_FILE, tmpSubDir);
        try {
            // Preconditions
            Assert.assertTrue(readMeTxt.exists());
            Assert.assertTrue(dirOne.exists());
            Assert.assertTrue(dirOne.isDirectory());
            Assert.assertTrue(dirTwo.exists());
            Assert.assertTrue(dirTwo.isDirectory());
            Assert.assertTrue(dirThree.exists());
            Assert.assertTrue(dirThree.isDirectory());
            Assert.assertTrue(fileOne.exists());
            Assert.assertTrue(fileTwo.exists());
            Assert.assertTrue(fileThree.exists());

            // Run function under test
            Utils4J.zipDir(tmpSubDir, destPath, destFile);

            // Unzip the result file
            Utils4J.unzip(destFile, tmpDir);

            // Postconditions
            Assert.assertTrue(new File(destSubDir, "readme.txt").exists());
            Assert.assertTrue(new File(destSubDir, "one").exists());
            Assert.assertTrue(new File(destSubDir, "one").isDirectory());
            Assert.assertTrue(new File(destSubDir, "two").exists());
            Assert.assertTrue(new File(destSubDir, "two").isDirectory());
            Assert.assertTrue(new File(destSubDir, "three").exists());
            Assert.assertTrue(new File(destSubDir, "three").isDirectory());
            Assert.assertTrue(new File(dirOne, "1.txt").exists());
            Assert.assertTrue(new File(dirTwo, "2.txt").exists());
            Assert.assertTrue(new File(dirThree, "3.txt").exists());

        } finally {
            // Cleanup
            FileUtils.deleteDirectory(tmpSubDir);
            FileUtils.deleteDirectory(destSubDir);
            destFile.delete();
        }

    }

    /**
     * @testng.test
     */
    public final void testGetUserHomeDir() {
        Assert.assertEquals(new File(System.getProperty("user.home")), Utils4J.getUserHomeDir());
    }

    /**
     * @testng.test
     */
    public final void testGetTempDir() {
        Assert.assertEquals(new File(System.getProperty("java.io.tmpdir")), Utils4J.getTempDir());
    }

    /**
     * @testng.test
     */
    public final void testConcatPathAndFilename() {
        Assert.assertEquals("xyz.jar",
                Utils4J.concatPathAndFilename(null, "xyz.jar", File.separator));
        Assert.assertEquals("xyz.jar", Utils4J.concatPathAndFilename("", "xyz.jar", File.separator));
        Assert.assertEquals("one" + File.separator + "xyz.jar",
                Utils4J.concatPathAndFilename("one", "xyz.jar", File.separator));
        Assert.assertEquals("one" + File.separator + "xyz.jar",
                Utils4J.concatPathAndFilename("one", "xyz.jar", File.separator));
    }

    /**
     * Create a runnable that locks the file.
     * 
     * @param file
     *            File to lock.
     * @param ec
     *            If an exception occurs it will stored in this object.
     * @param tryLockMax
     *            Number of tries to lock before throwing an exception.
     * @param tryWaitMillis
     *            Milliseconds to sleep between retries.
     * @param sleepMillis
     *            Number of milliseconds to hold the lock.
     * 
     * @return New runnable instance.
     */
    private Runnable createLockRunnable(final File file, final ExceptionContainer ec,
            final int tryLockMax, final long tryWaitMillis, final long sleepMillis) {
        return new Runnable() {
            public void run() {
                try {
                    final RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    try {
                        final FileLock lock = Utils4J.lockRandomAccessFile(raf, tryLockMax,
                                tryWaitMillis);
                        try {
                            // Hold the lock for one second
                            Thread.sleep(sleepMillis);
                        } finally {
                            lock.release();
                        }
                    } finally {
                        raf.close();
                    }
                } catch (final Exception ex) {
                    ec.exception = ex;
                }
            }
        };
    }

    /**
     * Start the two threads and wait until both finished.
     * 
     * @param thread1
     *            First thread.
     * @param thread2
     *            Second thread.
     */
    private static void startAndWaitUntilFinished(final Thread thread1, final Thread thread2) {

        try {

            // Start the threads
            thread1.start();
            Thread.sleep(200);
            thread2.start();

            // Wait for both to terminate
            thread1.join();
            thread2.join();

        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * @testng.test
     */
    public final void testLockRandomAccessFile() throws IOException {

        final File file = File.createTempFile("testLockRandomAccessFile", ".bin");

        try {

            final ExceptionContainer ec1 = new ExceptionContainer();
            // First holds a lock for one second
            final Thread thread1 = new Thread(createLockRunnable(file, ec1, 3, 100, 1000));

            final ExceptionContainer ec2 = new ExceptionContainer();
            // Second makes three tries to get the lock and retries after a
            // second
            final Thread thread2 = new Thread(createLockRunnable(file, ec2, 3, 1000, 0));

            // Start both threads to simulate a concurrent lock
            startAndWaitUntilFinished(thread1, thread2);

            // Check results
            Assert.assertEquals(ec1.exception, null);
            Assert.assertEquals(ec2.exception, null);

        } finally {
            file.delete();
        }

    }

    /**
     * @testng.test expectedExceptions="org.fuin.utils4j.LockingFailedException"
     */
    public final void testLockRandomAccessFileFailed() throws Exception {

        final File file = File.createTempFile("testLockRandomAccessFile", ".bin");

        try {

            final ExceptionContainer ec1 = new ExceptionContainer();
            // First holds a lock for one second
            final Thread thread1 = new Thread(createLockRunnable(file, ec1, 3, 100, 1000));

            final ExceptionContainer ec2 = new ExceptionContainer();
            // Second makes only one try to get the lock
            final Thread thread2 = new Thread(createLockRunnable(file, ec2, 1, 0, 0));

            // Start both threads to simulate a concurrent lock
            startAndWaitUntilFinished(thread1, thread2);

            // Check results
            Assert.assertEquals(ec1.exception, null);
            if (ec2.exception != null) {
                throw ec2.exception;
            }
            Assert.fail("Expected " + LockingFailedException.class.getName());

        } finally {
            file.delete();
        }

    }

    /**
     * @testng.test
     */
    public final void testEncodeDecodeHex() {
        final String str1 = "This is an example! 123456789-!\"?()[]&";
        final byte[] data1 = str1.getBytes();
        final String hex1 = Utils4J.encodeHex(data1);
        Assert.assertEquals(
                "5468697320697320616e206578616d706c6521203132333435363738392d21223f28295b5d26",
                hex1);
        final byte[] data2 = Utils4J.decodeHex(hex1);
        final String str2 = new String(data2);
        Assert.assertEquals(str1, str2);
    }

    /**
     * @testng.test
     */
    public final void testEncryptDecryptPasswordBased() {

        final String algorithm = "PBEWithMD5AndDES";
        final String str1 = "This is a secret text 1234567890-ÄÖÜäöüß";
        final byte[] data1 = str1.getBytes();
        final char[] password = "MyVerySecretPw!".toCharArray();
        final byte[] salt = new byte[] { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
                (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
        final int count = 100;

        final byte[] encrypted = Utils4J.encryptPasswordBased(algorithm, data1, password, salt,
                count);

        final byte[] decrypted = Utils4J.decryptPasswordBased(algorithm, encrypted, password, salt,
                count);

        final String str2 = new String(decrypted);
        Assert.assertEquals(str1, str2);

    }

    private static class ExceptionContainer {
        // Synchronizing is not necessary because only one thread is accessing
        // the variable
        public Exception exception = null;
    }

}
// CHECKSTYLE:ON
