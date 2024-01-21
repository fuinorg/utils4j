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

import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link JandexUtils}.
 */
// CHECKSTYLE:OFF
public class JandexUtilsTest {

    private static final File TARGET_DIR = new File("target");

    private static final File CLASSES_DIR = new File(TARGET_DIR, "classes");

    private static final File TEST_CLASSES_DIR = new File(TARGET_DIR, "test-classes");

    @Test
    public final void testIndexClassFile() {

        // PREPARE
        final List<File> knownFiles = new ArrayList<File>();
        final File classFile = new File(CLASSES_DIR, JandexUtils.class.getName().replace('.', '/') + ".class");
        final Indexer indexer = new Indexer();

        // TEST
        assertThat(JandexUtils.indexClassFile(indexer, knownFiles, classFile)).isTrue();
        assertThat(JandexUtils.indexClassFile(indexer, knownFiles, classFile)).isFalse();

        // VERIFY
        final Index index = indexer.complete();
        assertThat(knownFiles).contains(classFile);
        assertThat(index.getClassByName(DotName.createSimple(JandexUtils.class.getName()))).isNotNull();

    }

    @Test
    public final void testIndexDirSimple() {

        // TEST
        final Index index = JandexUtils.indexDir(CLASSES_DIR);

        // VERIFY
        assertThat(index.getClassByName(DotName.createSimple(JandexUtils.class.getName()))).isNotNull();
        assertThat(index.getClassByName(DotName.createSimple(Utils4J.class.getName()))).isNotNull();

    }

    @Test
    public final void testIndexDir() {

        // PREPARE
        final File jandexClassFile = new File(CLASSES_DIR, JandexUtils.class.getName().replace('.', '/') + ".class");
        final File utils4JClassFile = new File(CLASSES_DIR, Utils4J.class.getName().replace('.', '/') + ".class");
        final List<File> knownFiles = new ArrayList<File>();
        final File classesDir = new File("target/classes");
        final Indexer indexer = new Indexer();

        // TEST
        JandexUtils.indexDir(indexer, knownFiles, classesDir);

        // VERIFY
        final Index index = indexer.complete();
        assertThat(knownFiles).contains(jandexClassFile);
        assertThat(knownFiles).contains(utils4JClassFile);
        assertThat(index.getClassByName(DotName.createSimple(JandexUtils.class.getName()))).isNotNull();
        assertThat(index.getClassByName(DotName.createSimple(Utils4J.class.getName()))).isNotNull();

    }

    @Test
    public final void testIndexJar() {

        // PREPARE
        final File jarFile = new File("src/test/resources/ext4logback-0.2.0.jar");
        final List<File> knownFiles = new ArrayList<File>();
        final Indexer indexer = new Indexer();

        // TEST
        assertThat(JandexUtils.indexJar(indexer, knownFiles, jarFile)).isTrue();
        assertThat(JandexUtils.indexJar(indexer, knownFiles, jarFile)).isFalse();

        // VERIFY
        final Index index = indexer.complete();
        assertThat(knownFiles).contains(jarFile);
        assertThat(index.getClassByName(DotName.createSimple("org.fuin.ext4logback.LogbackStandalone"))).isNotNull();

    }

    @Test
    public final void testIndexClasspath() throws IOException {

        // PREPARE
        final File jandexClassFile = new File(CLASSES_DIR, JandexUtils.class.getName().replace('.', '/') + ".class")
                .getCanonicalFile();
        final List<File> knownFiles = new ArrayList<File>();
        final Indexer indexer = new Indexer();

        // TEST
        JandexUtils.indexClasspath(indexer, knownFiles);

        // VERIFY
        final Index index = indexer.complete();
        assertThat(index.getClassByName(DotName.createSimple(JandexUtils.class.getName()))).isNotNull();
        assertThat(knownFiles).contains(jandexClassFile);

    }

    @Test
    public final void loadClassOK() {
        assertThat(JandexUtils.loadClass(DotName.createSimple(Utils4J.class))).isEqualTo(Utils4J.class);
    }

    @Test
    public final void loadClassFailure() {
        assertThatThrownBy(() -> JandexUtils.loadClass(DotName.createSimple("a.b.c.d.DoesNotExist")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to load class");
    }

    @Test
    void testWriteIndexR() throws IOException {

        // PREPARE
        final Indexer indexer = new Indexer();
        indexer.indexClass(JandexUtils.class);
        final Index index = indexer.complete();
        final String filename = this.getClass().getSimpleName() + ".index";
        final File file = new File(CLASSES_DIR, filename);
        file.delete();

        // TEST
        JandexUtils.writeIndexR(file, index);

        // VERIFY
        assertThat(file).exists();
        final Index result = JandexUtils.loadIndexResourceR(filename);
        assertThat(result.getClassByName(JandexUtils.class.getName())).isNotNull();
        assertThat(result.getClassByName(JandexUtils.class.getName()).name().toString()).isEqualTo(JandexUtils.class.getName());

    }

    @Test
    public final void loadIndexResource() throws IOException {
        final Index index = JandexUtils.loadIndexResource("sample.index");
        assertThat(index.getClassByName(JandexUtils.class.getName())).isNotNull();
        assertThat(index.getClassByName(JandexUtils.class.getName()).name().toString()).isEqualTo(JandexUtils.class.getName());
    }

    @Test
    public final void loadIndexResourceR() {
        final Index index = JandexUtils.loadIndexResourceR("sample.index");
        assertThat(index.getClassByName(JandexUtils.class.getName())).isNotNull();
        assertThat(index.getClassByName(JandexUtils.class.getName()).name().toString()).isEqualTo(JandexUtils.class.getName());
    }

    @Test
    public final void loadIndexResourceFailure() {
        assertThatThrownBy(() -> JandexUtils.loadIndexResource("does-not-exist.index"))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessage("Resource not found: does-not-exist.index");
    }

    @Test
    public final void loadIndexResourceRFailure() {
        assertThatThrownBy(() -> JandexUtils.loadIndexResourceR("does-not-exist.index"))
                .isInstanceOf(RuntimeException.class)
                .hasRootCauseInstanceOf(FileNotFoundException.class)
                .hasRootCauseMessage("Resource not found: does-not-exist.index");
    }

}
// CHECKSTYLE:ON
