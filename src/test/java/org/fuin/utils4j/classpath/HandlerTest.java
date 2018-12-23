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
package org.fuin.utils4j.classpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.fuin.utils4j.classpath.Handler;
import org.junit.Test;

// CHECKSTYLE:OFF
public class HandlerTest {

    @Test
    public void testRead() throws IOException {

        final String original = System.getProperties().getProperty(Handler.HANDLER_PKGS, "");
        try {

            // PREPARE
            Handler.add();
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).contains(Handler.PKG);
            final URL url = new URL("classpath:/org/fuin/utils4j/test.properties");

            // TEST
            final List<String> lines = IOUtils.readLines(url.openStream(), Charset.forName("utf-8"));

            // VERIFY
            assertThat(lines).contains("one=1", "two=2", "three=3");

        } finally {
            System.getProperties().put(Handler.HANDLER_PKGS, original);
        }

    }

    @Test
    public void testAddTwice() {

        final String original = System.getProperties().getProperty(Handler.HANDLER_PKGS, "");
        try {

            // PREPARE
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).doesNotContain(Handler.PKG);

            // TEST
            Handler.add();
            Handler.add();

            // VERIFY
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).contains(Handler.PKG);
            int idx = System.getProperty(Handler.HANDLER_PKGS, "").indexOf(Handler.PKG);
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "").indexOf(Handler.PKG, idx + 1)).isEqualTo(-1);

        } finally {
            System.getProperties().put(Handler.HANDLER_PKGS, original);
        }

    }

    @Test
    public void testRemoveOnlyOne() {

        final String original = System.getProperties().getProperty(Handler.HANDLER_PKGS, "");
        try {
            // PREPARE
            System.getProperties().put(Handler.HANDLER_PKGS, Handler.PKG);

            // TEST
            Handler.remove();

            // VERIFY
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).isEmpty();

        } finally {
            System.getProperties().put(Handler.HANDLER_PKGS, original);
        }

    }

    @Test
    public void testRemoveBeginning() {

        final String original = System.getProperties().getProperty(Handler.HANDLER_PKGS, "");
        try {
            // PREPARE
            System.getProperties().put(Handler.HANDLER_PKGS, Handler.PKG + "|def");

            // TEST
            Handler.remove();

            // VERIFY
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).doesNotContain(Handler.PKG);
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).isEqualTo("def");

        } finally {
            System.getProperties().put(Handler.HANDLER_PKGS, original);
        }

    }

    @Test
    public void testRemoveMiddle() {

        final String original = System.getProperties().getProperty(Handler.HANDLER_PKGS, "");
        try {
            // PREPARE
            System.getProperties().put(Handler.HANDLER_PKGS, "abc|" + Handler.PKG + "|def");

            // TEST
            Handler.remove();

            // VERIFY
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).doesNotContain(Handler.PKG);
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).isEqualTo("abc|def");

        } finally {
            System.getProperties().put(Handler.HANDLER_PKGS, original);
        }

    }

    @Test
    public void testRemoveEnd() {

        final String original = System.getProperties().getProperty(Handler.HANDLER_PKGS, "");
        try {
            // PREPARE
            System.getProperties().put(Handler.HANDLER_PKGS, "abc|" + Handler.PKG);

            // TEST
            Handler.remove();

            // VERIFY
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).doesNotContain(Handler.PKG);
            assertThat(System.getProperty(Handler.HANDLER_PKGS, "")).isEqualTo("abc");

        } finally {
            System.getProperties().put(Handler.HANDLER_PKGS, original);
        }

    }

}
// CHECKSTYLE:ON
