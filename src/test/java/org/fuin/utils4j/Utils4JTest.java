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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.fuin.utils4j.test.ClassWithPrivateConstructor;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for Utils4J.
 */
// CHECKSTYLE:OFF
public class Utils4JTest {

    private static final File TEST_PROPERTIES_FILE = new File(
            "src/test/resources/org/fuin/utils4j/test.properties");

    private static final File ZIP_FILE = new File("src/test/resources/test.zip");

    private static final Map<String, String> vars = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        vars.put("one", "1");
        vars.put("two", "2");
        vars.put("3", "three");
    }

    @Test
    public final void testReplaceVarsNull() {
        assertThat(Utils4J.replaceVars(null, vars)).isNull();
    }

    @Test
    public final void testReplaceVarsEmpty() {
        assertThat(Utils4J.replaceVars("", vars)).isEqualTo("");
    }

    @Test
    public final void testReplaceVarsNoVars() {
        assertThat(Utils4J.replaceVars("one two three", vars)).isEqualTo("one two three");
    }

    @Test
    public final void testReplaceVarsSingleVar1() {
        assertThat(Utils4J.replaceVars("${one}", vars)).isEqualTo("1");
    }

    @Test
    public final void testReplaceVarsSingleVar2() {
        assertThat(Utils4J.replaceVars(" ${one}", vars)).isEqualTo(" 1");
    }

    @Test
    public final void testReplaceVarsSingleVar3() {
        assertThat(Utils4J.replaceVars("${one} ", vars)).isEqualTo("1 ");
    }

    @Test
    public final void testReplaceVarsSingleVar4() {
        assertThat(Utils4J.replaceVars(" ${one} ", vars)).isEqualTo(" 1 ");
    }

    @Test
    public final void testReplaceVarsMultipleVars1() {
        assertThat(Utils4J.replaceVars(" ${one} ${two} ${3} ", vars)).isEqualTo(" 1 2 three ");
    }

    @Test
    public final void testReplaceVarsMultipleVars2() {
        assertThat(Utils4J.replaceVars("${one} ${two} ${3} ", vars)).isEqualTo("1 2 three ");
    }

    @Test
    public final void testReplaceVarsMultipleVars3() {
        assertThat(Utils4J.replaceVars("${one}${two}${3}", vars)).isEqualTo("12three");
    }

    @Test
    public final void testReplaceVarsMultipleVars4() {
        assertThat(Utils4J.replaceVars(" ${one} ${two} ${3}", vars)).isEqualTo(" 1 2 three");
    }

    @Test
    public final void testReplaceVarsUnknown1() {
        assertThat(Utils4J.replaceVars("${xyz}", vars)).isEqualTo("${xyz}");
    }

    @Test
    public final void testReplaceVarsUnknown2() {
        assertThat(Utils4J.replaceVars("${one}${xyz}", vars)).isEqualTo("1${xyz}");
    }

    @Test
    public final void testReplaceVarsUnknown3() {
        assertThat(Utils4J.replaceVars("${xyz}${two}", vars)).isEqualTo("${xyz}2");
    }

    @Test
    public final void testReplaceVarsUnknown4() {
        assertThat(Utils4J.replaceVars("${one}${xyz}${two}", vars)).isEqualTo("1${xyz}2");
    }

    @Test
    public final void testReplaceVarsNoClosingBracket() {
        assertThat(Utils4J.replaceVars("${one}${two", vars)).isEqualTo("1${two");
    }

    @Test
    public final void testGetPackagePath() {
        assertThat(Utils4J.getPackagePath(Utils4J.class)).isEqualTo("org/fuin/utils4j");
    }

    @Test
    public final void testGetResource() throws IOException {
        final URL url = new File(new File(".").getCanonicalPath(),
                "target/test-classes/org/fuin/utils4j/test.properties").toURI().toURL();
        assertThat(Utils4J.getResource(Utils4JTest.class, "test.properties")).isEqualTo(url);
    }

    @Test
    public final void testCheckValidFileOK() {
        Utils4J.checkValidFile(TEST_PROPERTIES_FILE);
    }

    @Test
    public final void testCheckValidFileNotExisting() {
        try {
            Utils4J.checkValidFile(new File(TEST_PROPERTIES_FILE.getParentFile(), "foobar.txt"));
            fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public final void testCheckValidFileDirectory() {
        try {
            Utils4J.checkValidFile(TEST_PROPERTIES_FILE.getParentFile());
            fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public final void testCheckValidDirOK() {
        Utils4J.checkValidDir(TEST_PROPERTIES_FILE.getParentFile());
    }

    @Test
    public final void testCheckValidDirNotExisting() {
        try {
            Utils4J.checkValidDir(new File(TEST_PROPERTIES_FILE.getParentFile(), "foobar"));
            fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public final void testCheckValidDirFile() {
        try {
            Utils4J.checkValidDir(TEST_PROPERTIES_FILE);
            fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public final void testCreateInstanceOK() {
        final Object obj = Utils4J.createInstance(Utils4JTest.class.getName());
        assertThat(obj).isInstanceOf(Utils4JTest.class);
    }

    @Test
    public final void testCreateInstanceClassNotFound() {
        try {
            Utils4J.createInstance("x.y.Z");
            fail();
        } catch (final RuntimeException ex) {
            assertThat(ex.getCause()).isInstanceOf(ClassNotFoundException.class);
        }
    }

    @Test
    public final void testCreateInstanceInstantiationProblem() {
        try {
            Utils4J.createInstance(Cancelable.class.getName());
            fail();
        } catch (final RuntimeException ex) {
            assertThat(ex.getCause()).isInstanceOf(InstantiationException.class);
        }
    }

    @Test
    public final void testCreateInstanceIllegalAccess() {
        try {
            Utils4J.createInstance(ClassWithPrivateConstructor.class.getName());
            fail();
        } catch (final RuntimeException ex) {
            assertThat(ex.getCause()).isInstanceOf(IllegalAccessException.class);
        }
    }

    @Test
    public final void testContainsURL() throws IOException {
        final URL[] urls = new URL[] { new URL("http://www.google.com"), new URL("http://www.yahoo.com"),
                new URL("file:/foobar.txt") };
        assertThat(Utils4J.containsURL(urls, new URL("http://www.google.com"))).isTrue();
        assertThat(Utils4J.containsURL(urls, new URL("http://www.google.com/"))).isFalse();
        assertThat(Utils4J.containsURL(urls, new URL("http://www.abc.com"))).isFalse();
    }

    @Test
    public final void testCreateHash() {
        assertThat(Utils4J.createHashMD5(TEST_PROPERTIES_FILE)).isEqualTo("e13c4b796b94a61b9d0050941676d129");
    }

    @Test
    public final void testCreateUrlDirFile() throws IOException {
        assertThat(Utils4J.createUrl(new URL("http://www.fuin.org"), "test", "index.html"))
                .isEqualTo(new URL("http://www.fuin.org/test/index.html"));
        assertThat(Utils4J.createUrl(new URL("http://www.fuin.org/"), "test", "index.html"))
                .isEqualTo(new URL("http://www.fuin.org/test/index.html"));
    }

    @Test
    public final void testCreateUrlNullDirFile() throws IOException {
        assertThat(Utils4J.createUrl(new URL("http://www.fuin.org"), null, "index.html"))
                .isEqualTo(new URL("http://www.fuin.org/index.html"));
        assertThat(Utils4J.createUrl(new URL("http://www.fuin.org/"), null, "index.html"))
                .isEqualTo(new URL("http://www.fuin.org/index.html"));
    }

    @Test
    public final void testCreateUrlEmptyDirFile() throws IOException {
        assertThat(Utils4J.createUrl(new URL("http://www.fuin.org"), "", "index.html"))
                .isEqualTo(new URL("http://www.fuin.org/index.html"));
        assertThat(Utils4J.createUrl(new URL("http://www.fuin.org/"), "", "index.html"))
                .isEqualTo(new URL("http://www.fuin.org/index.html"));
    }

    @Test
    public final void testGetRelativePathOK() {
        assertThat(Utils4J.getRelativePath(
                TEST_PROPERTIES_FILE.getParentFile().getParentFile().getParentFile().getParentFile(),
                TEST_PROPERTIES_FILE.getParentFile()))
                        .isEqualTo("org" + File.separator + "fuin" + File.separator + "utils4j");
    }

    @Test
    public final void testGetBackToRootPath() {
        assertThat(Utils4J.getBackToRootPath("a/b/c", '/')).isEqualTo("../../..");
        assertThat(Utils4J.getBackToRootPath("a/b/", '/')).isEqualTo("../../");
        assertThat(Utils4J.getBackToRootPath("a", '/')).isEqualTo("..");
        assertThat(Utils4J.getBackToRootPath("", '/')).isEqualTo("");
    }

    @Test
    public final void testGetRelativePathSame() {
        assertThat(Utils4J.getRelativePath(TEST_PROPERTIES_FILE.getParentFile(),
                TEST_PROPERTIES_FILE.getParentFile())).isEqualTo("");
    }

    @Test
    public final void testGetRelativePathNotInsideBaseDir() {
        try {
            Utils4J.getRelativePath(TEST_PROPERTIES_FILE.getParentFile(),
                    TEST_PROPERTIES_FILE.getParentFile().getParentFile());
            fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    @SuppressWarnings("resource")
    public final void testAddToClasspathString() throws MalformedURLException {
        final ClassLoader classLoader = Utils4J.class.getClassLoader();
        if (!(classLoader instanceof URLClassLoader)) {
            throw new IllegalStateException(
                    "Classloader is not an URL classloader! [" + classLoader.getClass().getName() + "]");
        }
        final URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
        final URL url = new URL("file:/test1.jar");
        assertThat(Utils4J.containsURL(urlClassLoader.getURLs(), url)).isFalse();
        Utils4J.addToClasspath(url.toExternalForm());
        assertThat(Utils4J.containsURL(urlClassLoader.getURLs(), url)).isTrue();
    }

    @Test
    @SuppressWarnings("resource")
    public final void testAddToClasspathURL() throws MalformedURLException {
        final ClassLoader classLoader = Utils4J.class.getClassLoader();
        if (!(classLoader instanceof URLClassLoader)) {
            throw new IllegalStateException(
                    "Classloader is not an URL classloader! [" + classLoader.getClass().getName() + "]");
        }
        final URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
        final URL url = new URL("file:/test2.jar");
        assertThat(Utils4J.containsURL(urlClassLoader.getURLs(), url)).isFalse();
        Utils4J.addToClasspath(url);
        assertThat(Utils4J.containsURL(urlClassLoader.getURLs(), url)).isTrue();
    }

    @Test
    public final void testCheckNotNullOK() {
        Utils4J.checkNotNull("name", "123");
    }

    @Test
    public final void testCheckNotNullFail() {
        try {
            Utils4J.checkNotNull("name", null);
            fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public final void testCheckNotEmptyOK() {
        Utils4J.checkNotEmpty("name", "abc");
    }

    @Test
    public final void testCheckNotEmptyFail() {
        try {
            Utils4J.checkNotEmpty("name", "");
            fail();
        } catch (final IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public final void testInvokeOK() throws InvokeMethodFailedException {
        assertThat(Utils4J.invoke(new IllegalNullArgumentException("abc"), "getArgument", new Class[] {},
                new Object[] {})).isEqualTo("abc");
    }

    @Test
    public final void testInvokeFail() throws InvokeMethodFailedException {
        try {
            Utils4J.invoke(new IllegalNullArgumentException("abc"), "getArgument",
                    new Class[] { String.class }, new Object[] { "" });
            fail();
        } catch (final InvokeMethodFailedException ex) {
            // OK
        }
    }

    @Test
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
        assertThat(readMeTxt.exists()).isFalse();
        assertThat(dirOne.exists()).isFalse();
        assertThat(dirTwo.exists()).isFalse();
        assertThat(dirThree.exists()).isFalse();
        assertThat(fileOne.exists()).isFalse();
        assertThat(fileTwo.exists()).isFalse();
        assertThat(fileThree.exists()).isFalse();

        // Execute method
        Utils4J.unzip(ZIP_FILE, tmpDir);

        try {

            // Postconditions
            assertThat(readMeTxt.exists()).isTrue();
            assertThat(dirOne.exists()).isTrue();
            assertThat(dirOne.isDirectory()).isTrue();
            assertThat(dirTwo.exists()).isTrue();
            assertThat(dirTwo.isDirectory()).isTrue();
            assertThat(dirThree.exists()).isTrue();
            assertThat(dirThree.isDirectory()).isTrue();
            assertThat(fileOne.exists()).isTrue();
            assertThat(fileTwo.exists()).isTrue();
            assertThat(fileThree.exists()).isTrue();

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

    @Test
    public final void testZip() throws IOException {

        final File tmpDir = Utils4J.getTempDir();
        final File tmpSubDir = new File(tmpDir, "test");
        if (tmpSubDir.exists()) {
            FileUtils.deleteDirectory(tmpSubDir);
        }
        assertThat(tmpSubDir.mkdir()).isTrue();

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
            assertThat(readMeTxt.exists()).isTrue();
            assertThat(dirOne.exists()).isTrue();
            assertThat(dirOne.isDirectory()).isTrue();
            assertThat(dirTwo.exists()).isTrue();
            assertThat(dirTwo.isDirectory()).isTrue();
            assertThat(dirThree.exists()).isTrue();
            assertThat(dirThree.isDirectory()).isTrue();
            assertThat(fileOne.exists()).isTrue();
            assertThat(fileTwo.exists()).isTrue();
            assertThat(fileThree.exists()).isTrue();

            // Run function under test
            Utils4J.zipDir(tmpSubDir, destPath, destFile);

            // Unzip the result file
            Utils4J.unzip(destFile, tmpDir);

            // Postconditions
            assertThat(new File(destSubDir, "readme.txt").exists()).isTrue();
            assertThat(new File(destSubDir, "one").exists()).isTrue();
            assertThat(new File(destSubDir, "one").isDirectory()).isTrue();
            assertThat(new File(destSubDir, "two").exists()).isTrue();
            assertThat(new File(destSubDir, "two").isDirectory()).isTrue();
            assertThat(new File(destSubDir, "three").exists()).isTrue();
            assertThat(new File(destSubDir, "three").isDirectory()).isTrue();
            assertThat(new File(dirOne, "1.txt").exists()).isTrue();
            assertThat(new File(dirTwo, "2.txt").exists()).isTrue();
            assertThat(new File(dirThree, "3.txt").exists()).isTrue();

        } finally {
            // Cleanup
            FileUtils.deleteDirectory(tmpSubDir);
            FileUtils.deleteDirectory(destSubDir);
            destFile.delete();
        }

    }

    @Test
    public final void testGetUserHomeDir() {
        assertThat(Utils4J.getUserHomeDir()).isEqualTo(new File(System.getProperty("user.home")));
    }

    @Test
    public final void testGetTempDir() {
        assertThat(Utils4J.getTempDir()).isEqualTo(new File(System.getProperty("java.io.tmpdir")));
    }

    @Test
    public final void testConcatPathAndFilename() {
        assertThat(Utils4J.concatPathAndFilename(null, "xyz.jar", File.separator)).isEqualTo("xyz.jar");
        assertThat(Utils4J.concatPathAndFilename("", "xyz.jar", File.separator)).isEqualTo("xyz.jar");
        assertThat(Utils4J.concatPathAndFilename("one", "xyz.jar", File.separator))
                .isEqualTo("one" + File.separator + "xyz.jar");
        assertThat(Utils4J.concatPathAndFilename("one", "xyz.jar", File.separator))
                .isEqualTo("one" + File.separator + "xyz.jar");
    }

    @Test
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
            assertThat(ec1.exception).isNull();
            assertThat(ec2.exception).isNull();

        } finally {
            file.delete();
        }

    }

    @Test(expected = LockingFailedException.class)
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
            assertThat(ec1.exception).isNull();
            if (ec2.exception != null) {
                throw ec2.exception;
            }
            fail("Expected " + LockingFailedException.class.getName());

        } finally {
            file.delete();
        }

    }

    @Test
    public final void testEncodeDecodeHex() {
        final String str1 = "This is an example! 123456789-!\"?()[]&";
        final byte[] data1 = str1.getBytes();
        final String hex1 = Utils4J.encodeHex(data1);
        assertThat(hex1)
                .isEqualTo("5468697320697320616e206578616d706c6521203132333435363738392d21223f28295b5d26");
        final byte[] data2 = Utils4J.decodeHex(hex1);
        final String str2 = new String(data2);
        assertThat(str2).isEqualTo(str1);
    }

    @Test
    public final void testEncryptDecryptPasswordBased() {

        final String algorithm = "PBEWithMD5AndDES";
        final String str1 = "This is a secret text 1234567890-ÄÖÜäöüß";
        final byte[] data1 = str1.getBytes();
        final char[] password = "MyVerySecretPw!".toCharArray();
        final byte[] salt = new byte[] { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c, (byte) 0x7e,
                (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
        final int count = 100;

        final byte[] encrypted = Utils4J.encryptPasswordBased(algorithm, data1, password, salt, count);

        final byte[] decrypted = Utils4J.decryptPasswordBased(algorithm, encrypted, password, salt, count);

        final String str2 = new String(decrypted);
        assertThat(str2).isEqualTo(str1);

    }

    @Test
    public void testFileInsideDirectory() {

        assertThat(Utils4J.fileInsideDirectory(new File("/"), new File("/a/b/c/fileX.txt"))).isTrue();
        assertThat(Utils4J.fileInsideDirectory(new File("/"), new File("/fileX.txt"))).isTrue();
        assertThat(Utils4J.fileInsideDirectory(new File("/a"), new File("/a/b/c/fileX.txt"))).isTrue();
        assertThat(Utils4J.fileInsideDirectory(new File("/a/b"), new File("/a/b/c/fileX.txt"))).isTrue();
        assertThat(Utils4J.fileInsideDirectory(new File("/a/b/c"), new File("/a/b/c/fileX.txt"))).isTrue();
        assertThat(Utils4J.fileInsideDirectory(new File("/a/b/c"), new File("/a/b/c/d/fileX.txt"))).isTrue();

        assertThat(Utils4J.fileInsideDirectory(new File("/a"), new File("/fileX.txt"))).isFalse();
        assertThat(Utils4J.fileInsideDirectory(new File("/a"), new File("/b/fileX.txt"))).isFalse();

    }

    @Test
    public void testGetCanonicalPath() {

        assertThat(Utils4J.getCanonicalPath(null)).isNull();
        assertThat(Utils4J.getCanonicalPath(new File("/a/b/c/fileX.txt")))
                .isEqualTo(getRoot() + "a/b/c/fileX.txt".replace('/', File.separatorChar));

    }

    @Test
    public void testGetCanonicalFile() {

        assertThat(Utils4J.getCanonicalFile(null)).isNull();
        assertThat(Utils4J.getCanonicalFile(new File("/a/b/c/fileX.txt")))
                .isEqualTo(new File(getRoot() + "a/b/c/fileX.txt".replace('/', File.separatorChar)));

    }

    @Test
    public void testReadAsString() throws MalformedURLException {

        // TEST
        final String text = Utils4J.readAsString(TEST_PROPERTIES_FILE.toURI().toURL(), "utf-8", 1024);

        // VERIFY
        assertThat(text).isEqualTo("one=1\r\ntwo=2\r\nthree=3\r\n");

    }

    @Test
    public void testUrl() throws MalformedURLException {

        // TEST
        final URL url = Utils4J.url("http://www.fuin.org/");

        // VERIFY
        assertThat(url).isEqualTo(new URL("http://www.fuin.org/"));

    }

    @Test
    public void testReplaceCrLfTab() {

        assertThat(Utils4J.replaceCrLfTab(null)).isNull();
        assertThat(Utils4J.replaceCrLfTab("")).isEqualTo("");
        assertThat(Utils4J.replaceCrLfTab("\r")).isEqualTo("\r");
        assertThat(Utils4J.replaceCrLfTab("\\r")).isEqualTo("\r");
        assertThat(Utils4J.replaceCrLfTab("\n")).isEqualTo("\n");
        assertThat(Utils4J.replaceCrLfTab("\\n")).isEqualTo("\n");
        assertThat(Utils4J.replaceCrLfTab("\t")).isEqualTo("\t");
        assertThat(Utils4J.replaceCrLfTab("\\t")).isEqualTo("\t");
        assertThat(Utils4J.replaceCrLfTab("a\\nb\\nc\\n")).isEqualTo("a\nb\nc\n");

    }

    @Test
    public void testDateToFileTime() {

        // PREPARE
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1601);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));

        // TEST & VERIFY
        assertThat(Utils4J.dateToFileTime(cal.getTime())).isEqualTo(0);

    }

    @Test
    public void testExpectedException() {

        assertThat(Utils4J.expectedException(new IOException(), null)).isTrue();
        assertThat(Utils4J.expectedException(new IOException(), new ArrayList<>())).isTrue();
        assertThat(Utils4J.expectedException(new IOException(), list(IOException.class))).isTrue();
        assertThat(Utils4J.expectedException(new FileNotFoundException(), list(IOException.class))).isTrue();
        assertThat(Utils4J.expectedException(new IOException(), list(FileNotFoundException.class))).isFalse();

    }

    @Test
    public void testExpectedCause() {

        assertThat(Utils4J.expectedCause(new RuntimeException(), list(IOException.class))).isFalse();
        assertThat(Utils4J.expectedCause(new RuntimeException(new IOException()), null)).isTrue();
        assertThat(Utils4J.expectedCause(new RuntimeException(new IOException()), new ArrayList<>()))
                .isTrue();
        assertThat(Utils4J.expectedCause(new RuntimeException(new IOException()), list(IOException.class)))
                .isTrue();
        assertThat(Utils4J.expectedCause(new RuntimeException(new FileNotFoundException()),
                list(IOException.class))).isTrue();
        assertThat(Utils4J.expectedCause(new RuntimeException(new IOException()),
                list(FileNotFoundException.class))).isFalse();

    }

    @SafeVarargs
    private static List<Class<? extends Exception>> list(Class<? extends Exception>... classes) {
        return Arrays.asList(classes);
    }

    private static String getRoot() {
        try {
            return new File("/").getCanonicalPath();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class ExceptionContainer {
        // Synchronizing is not necessary because only one thread is accessing
        // the variable
        public Exception exception = null;
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
    private Runnable createLockRunnable(final File file, final ExceptionContainer ec, final int tryLockMax,
            final long tryWaitMillis, final long sleepMillis) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    final RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    try {
                        final FileLock lock = Utils4J.lockRandomAccessFile(raf, tryLockMax, tryWaitMillis);
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

    @Test
    public void testJreFile() throws IOException {

        final File javaHomeDir = new File(System.getProperty("java.home"));
        final File javaExtDir = new File(javaHomeDir, "lib/ext");

        // Existing JRE files
        assertThat(Utils4J.jreFile(new File(javaHomeDir, "lib/rt.jar"))).isTrue();
        assertThat(Utils4J.jreFile(new File(javaExtDir, "localedata.jar"))).isTrue();

        // Non existing and not in JRE
        assertThat(Utils4J.jreFile(new File(Utils4J.getTempDir(), "whatever.jar"))).isFalse();

        // Non existing in JRE
        assertThat(Utils4J.jreFile(new File(javaHomeDir, "whatever.jar"))).isFalse();

        // Existing JAR, but not in JRE
        final File aJar = new File(Utils4J.getTempDir(), "a.jar");
        aJar.createNewFile();
        try {
            assertThat(Utils4J.jreJarFile(aJar)).isFalse();
        } finally {
            aJar.delete();
        }

    }

    @Test
    public void testClassFile() throws IOException {

        final File aClass = new File(Utils4J.getTempDir(), "a.class");
        aClass.createNewFile();
        final File aJar = new File(Utils4J.getTempDir(), "a.jar");
        aJar.createNewFile();
        try {
            assertThat(Utils4J.classFile(aClass)).isTrue();
            assertThat(Utils4J.classFile(aJar)).isFalse();
            assertThat(Utils4J.classFile(new File(Utils4J.getTempDir(), "b.jar"))).isFalse();
        } finally {
            aClass.delete();
            aJar.delete();
        }

    }

    @Test
    public void testJarFile() throws IOException {

        final File aClass = new File(Utils4J.getTempDir(), "a.class");
        aClass.createNewFile();
        final File aJar = new File(Utils4J.getTempDir(), "a.jar");
        aJar.createNewFile();
        try {
            assertThat(Utils4J.jarFile(aJar)).isTrue();
            assertThat(Utils4J.jarFile(aClass)).isFalse();
        } finally {
            aClass.delete();
            aJar.delete();
        }

    }

    @Test
    public void testNonJreJarFile() throws IOException {

        final File aClass = new File(Utils4J.getTempDir(), "a.class");
        aClass.createNewFile();
        final File aJar = new File(Utils4J.getTempDir(), "a.jar");
        aJar.createNewFile();
        try {
            assertThat(Utils4J.nonJreJarFile(aJar)).isTrue();
            assertThat(Utils4J.nonJreJarFile(aClass)).isFalse();
        } finally {
            aClass.delete();
            aJar.delete();
        }

    }

    @Test
    public void testJreJarFile() throws IOException {

        final File javaHomeDir = new File(System.getProperty("java.home"));
        final File javaExtDir = new File(javaHomeDir, "lib/ext");

        // Valid JRE JARs
        assertThat(Utils4J.jreJarFile(new File(javaHomeDir, "lib/rt.jar"))).isTrue();
        assertThat(Utils4J.jreJarFile(new File(javaExtDir, "localedata.jar"))).isTrue();

        // No jar
        assertThat(Utils4J.jreJarFile(new File(javaHomeDir, "README"))).isFalse();

        // Not existing
        assertThat(Utils4J.jreJarFile(new File(Utils4J.getTempDir(), "whatever.jar"))).isFalse();

        // Existing JAR, but not in JRE
        final File aJar = new File(Utils4J.getTempDir(), "a.jar");
        aJar.createNewFile();
        try {
            assertThat(Utils4J.jreJarFile(aJar)).isFalse();
        } finally {
            aJar.delete();
        }

    }

    @Test
    public void testClasspathFiles() throws IOException {

        final List<File> nonJreClassFiles = Utils4J.classpathFiles(Utils4J::classFile);
        final File thisClass = new File("./target/test-classes/org/fuin/utils4j/Utils4JTest.class")
                .getCanonicalFile();
        assertThat(nonJreClassFiles).contains(thisClass);

    }

    @Test
    public void testPathsFiles() {

        final File javaHomeDir = new File(System.getProperty("java.home"));

        final List<File> bootJarFiles = Utils4J.pathsFiles(System.getProperty("sun.boot.class.path"),
                Utils4J::jreJarFile);
        final File rtJar = new File(javaHomeDir, "lib/rt.jar");
        assertThat(bootJarFiles).contains(rtJar);

        final List<File> extJarFiles = Utils4J.pathsFiles(System.getProperty("java.ext.dirs"),
                Utils4J::jreJarFile);
        final File localedataJar = new File(javaHomeDir, "lib/ext/localedata.jar");
        assertThat(extJarFiles).contains(localedataJar);

    }

    @Test
    public void testLocalFilesFromUrlClassLoader() throws IOException {

        final List<File> files = Utils4J
                .localFilesFromUrlClassLoader((URLClassLoader) this.getClass().getClassLoader());
        assertThat(files).contains(new File("/test1.jar"));

    }

}
// CHECKSTYLE:ON
