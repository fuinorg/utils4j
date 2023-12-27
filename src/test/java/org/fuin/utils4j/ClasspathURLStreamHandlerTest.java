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

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

//CHECKSTYLE:OFF
public class ClasspathURLStreamHandlerTest {

    @Test
    public void testUrlClasspath() throws MalformedURLException {

        // TEST
        final URL url = Utils4J.url("classpath:org/fuin/utils4j/test.properties");

        // VERIFY
        final String text = Utils4J.readAsString(url, "utf-8", 1024);
        assertThat(text).isEqualTo("one=1\r\ntwo=2\r\nthree=3\r\n");

    }

}
// CHECKSTYLE:ON
