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

import org.fuin.utils4j.jandex.JandexIndexFileReader.Builder;

import org.jboss.jandex.IndexView;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JandexIndexFileReader} class.
 */
public class JandexIndexFileReaderTest {

    @Test
    public final void loadIndexFile() throws IOException {
        final IndexView index = new Builder().addFiles(new File("src/test/resources/sample.index")).build().load();
        assertThat(index.getClassByName(JandexUtils.class.getName())).isNotNull();
        assertThat(index.getClassByName(JandexUtils.class.getName()).name().toString()).isEqualTo(JandexUtils.class.getName());
    }

    @Test
    public final void loadIndexResource() throws IOException {
        final IndexView index = new Builder().addResources("sample.index").build().load();
        assertThat(index.getClassByName(JandexUtils.class.getName())).isNotNull();
        assertThat(index.getClassByName(JandexUtils.class.getName()).name().toString()).isEqualTo(JandexUtils.class.getName());
    }

    @Test
    public final void loadIndexResourceR() {
        final IndexView index = new Builder().addResources("sample.index").build().loadR();
        assertThat(index.getClassByName(JandexUtils.class.getName())).isNotNull();
        assertThat(index.getClassByName(JandexUtils.class.getName()).name().toString()).isEqualTo(JandexUtils.class.getName());
    }

    @Test
    public final void loadIndexResourceFailure() throws IOException {
        assertThat(new Builder().addResources("does-not-exist.index").build().load().getKnownClasses()).isEmpty();
    }

    @Test
    public final void loadIndexResourceRFailure() {
        assertThat(new Builder().addResources("does-not-exist.index").build().loadR().getKnownClasses()).isEmpty();
    }

}
