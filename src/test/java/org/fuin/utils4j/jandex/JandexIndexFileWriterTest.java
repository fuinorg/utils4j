/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.utils4j.jandex;

import org.fuin.utils4j.jandex.JandexIndexFileReader;
import org.fuin.utils4j.jandex.JandexIndexFileWriter;
import org.fuin.utils4j.jandex.JandexUtils;
import org.jboss.jandex.Index;
import org.jboss.jandex.IndexView;
import org.jboss.jandex.Indexer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JandexIndexFileWriter}.
 */
public class JandexIndexFileWriterTest {

    private static final File TARGET_DIR = new File("target");

    private static final File CLASSES_DIR = new File(TARGET_DIR, "classes");

    private static final File TEST_CLASSES_DIR = new File(TARGET_DIR, "test-classes");

    @Test
    void testWriteIndexR() throws IOException {

        // PREPARE
        final Indexer indexer = new Indexer();
        indexer.indexClass(JandexUtils.class);
        final Index index = indexer.complete();
        final String filename = this.getClass().getSimpleName() + ".index";
        final File file = new File(TEST_CLASSES_DIR, filename);
        file.delete();

        // TEST
        new JandexIndexFileWriter().writeIndexR(file, index);

        // VERIFY
        assertThat(file).exists();
        final IndexView result = new JandexIndexFileReader.Builder().addResources(filename).build().loadR();
        assertThat(result.getClassByName(JandexUtils.class.getName())).isNotNull();
        assertThat(result.getClassByName(JandexUtils.class.getName()).name().toString()).isEqualTo(JandexUtils.class.getName());

    }

}
