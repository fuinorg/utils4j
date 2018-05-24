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
import java.net.MalformedURLException;
import java.util.Properties;

import org.junit.Test;

/**
 * Tests for {@link PropertiesUtils}.
 */
// CHECKSTYLE:OFF
public class PropertiesUtilsTest {

    private static final File TEST_PROPERTIES_FILE = new File("src/test/resources/org/fuin/utils4j/test.properties");

    @Test
    public final void testLoadPropertiesClassString() {
        final Properties props = PropertiesUtils.loadProperties(PropertiesUtilsTest.class, "test.properties");
        assertThat(props.size()).isEqualTo(3);
        assertThat(props.get("one")).isEqualTo("1");
        assertThat(props.get("two")).isEqualTo("2");
        assertThat(props.get("three")).isEqualTo("3");
    }

    @Test
    public final void testLoadPropertiesClassStringNonExisting() {
        try {
            PropertiesUtils.loadProperties(PropertiesUtilsTest.class, "DoesNotExist.properties");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).contains("Resource");
            assertThat(ex.getMessage()).contains("not found");
        }
    }

    @Test
    public final void testLoadPropertiesFile() throws IOException {
        final Properties props = PropertiesUtils.loadProperties(TEST_PROPERTIES_FILE);
        assertThat(props.size()).isEqualTo(3);
        assertThat(props.get("one")).isEqualTo("1");
        assertThat(props.get("two")).isEqualTo("2");
        assertThat(props.get("three")).isEqualTo("3");
    }

    @Test
    public final void testSaveProperties() throws IOException {
        final File testFile = File.createTempFile(this.getClass().getSimpleName() + "-", ".properties");
        try {
            // Write to file
            Properties props = new Properties();
            props.put("eins", "1");
            props.put("zwei", "2");
            props.put("drei", "3");
            PropertiesUtils.saveProperties(testFile, props, "COMMENT");

            // Read it
            props = PropertiesUtils.loadProperties(testFile);
            assertThat(props).hasSize(3);
            assertThat(props.get("eins")).isEqualTo("1");
            assertThat(props.get("zwei")).isEqualTo("2");
            assertThat(props.get("drei")).isEqualTo("3");

        } finally {
            testFile.delete();
        }
    }

    @Test
    public final void testSavePropertiesNonExistingParentDirectory() throws IOException {
        try {
            final File parentDir = new File("thisdoesnotexist");
            PropertiesUtils.saveProperties(new File(parentDir, "dummy.properties"), new Properties(), "COMMENT");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).contains("The parent directory");
            assertThat(ex.getMessage()).contains("does not exist");
        }
    }

    @Test
    public final void testSavePropertiesIOexception() throws LockingFailedException, IOException {
        final File testFile = File.createTempFile(this.getClass().getSimpleName() + "-", ".properties");
        try {
            testFile.setReadOnly();
            try {
                PropertiesUtils.saveProperties(testFile, new Properties(), "COMMENT");
                fail();
            } catch (final RuntimeException ex) {
                assertThat(ex).hasCauseInstanceOf(FileNotFoundException.class);
            }
        } finally {
            testFile.delete();
        }
    }

    @Test
    public final void testLoadPropertiesURLString() throws MalformedURLException {
        final Properties props = PropertiesUtils.loadProperties(TEST_PROPERTIES_FILE.getParentFile().toURI().toURL(),
                TEST_PROPERTIES_FILE.getName());
        assertThat(props.size()).isEqualTo(3);
        assertThat(props.get("one")).isEqualTo("1");
        assertThat(props.get("two")).isEqualTo("2");
        assertThat(props.get("three")).isEqualTo("3");
    }

    @Test
    public final void testLoadPropertiesURL() throws MalformedURLException {
        final Properties props = PropertiesUtils.loadProperties(TEST_PROPERTIES_FILE.toURI().toURL());
        assertThat(props.size()).isEqualTo(3);
        assertThat(props.get("one")).isEqualTo("1");
        assertThat(props.get("two")).isEqualTo("2");
        assertThat(props.get("three")).isEqualTo("3");
    }

    @Test
    public final void testLoadPropertiesStringString() throws MalformedURLException {
        final Properties props = PropertiesUtils.loadProperties(TEST_PROPERTIES_FILE.getParentFile().toURI().toURL().toExternalForm(),
                TEST_PROPERTIES_FILE.getName());
        assertThat(props).hasSize(3);
        assertThat(props.get("one")).isEqualTo("1");
        assertThat(props.get("two")).isEqualTo("2");
        assertThat(props.get("three")).isEqualTo("3");
    }

    @Test
    public final void testLoadPropertiesStringStringIllegalUrl() throws MalformedURLException {
        try {
            PropertiesUtils.loadProperties("no-url", "dummy.properties");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).contains("The argument 'srcUrl' is not a valid URL");
        }
    }
}
// CHECKSTYLE:ON
