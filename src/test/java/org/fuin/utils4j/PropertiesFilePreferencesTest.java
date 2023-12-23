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
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//CHECKSTYLE:OFF
public final class PropertiesFilePreferencesTest {

    private static File baseDir;

    @BeforeAll
    public static void beforeClass() throws IOException {
        baseDir = new File(Utils4J.getTempDir(), "test-prop-file-pref");
    }

    @BeforeEach
    public final void beforeMethod() throws IOException {
        FileUtils.deleteDirectory(baseDir);
        baseDir.mkdirs();
    }

    @Test
    public final void testConstruction() throws BackingStoreException {
        final File dir = new File(baseDir, "user");
        final PropertiesFilePreferences pref = new PropertiesFilePreferences(dir);
        pref.put("one", "1");
        pref.put("two", "2");
        pref.put("three", "3");
        pref.flush();
        final File file = new File(dir, PropertiesFilePreferences.FILENAME);
        assertThat(file.exists()).isTrue();
        TestHelper.assertPropertiesEqual(file, pref.toProperties());
    }

    @Test
    public final void testChildConstruction() throws BackingStoreException {
        final File dir = new File(baseDir, "user");
        final File subDir = new File(dir, "abc");
        final File subDir2 = new File(subDir, "def");
        final File file = new File(subDir, PropertiesFilePreferences.FILENAME);
        final File file2 = new File(subDir2, PropertiesFilePreferences.FILENAME);

        final PropertiesFilePreferences root = new PropertiesFilePreferences(dir);

        // Create child node
        final Preferences pref = root.node("abc");
        pref.put("one", "A");
        pref.put("two", "B");
        pref.put("three", "C");

        final Preferences pref2 = pref.node("def");
        pref2.put("four", "4");

        // Flush root to save data to disk
        root.flush();

        assertThat(file.exists()).isTrue();
        assertThat(file2.exists()).isTrue();
        TestHelper.assertPropertiesEqual(file, ((PropertiesFilePreferences) pref).toProperties());
        TestHelper.assertPropertiesEqual(file2, ((PropertiesFilePreferences) pref2).toProperties());
    }

    @Test
    public final void testChildRemove() throws BackingStoreException {

        final File dir = new File(baseDir, "user");
        final File subDir = new File(dir, "abc");
        final File file = new File(subDir, PropertiesFilePreferences.FILENAME);
        final PropertiesFilePreferences root = new PropertiesFilePreferences(dir);

        final Preferences pref = root.node("abc");
        pref.flush();
        assertThat(file.exists()).isTrue();

        pref.removeNode();
        pref.flush();
        assertThat(file.exists()).isFalse();

    }

}
// CHECKSTYLE:ON
