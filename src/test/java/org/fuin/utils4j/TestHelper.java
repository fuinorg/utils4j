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

import static org.assertj.core.api.StrictAssertions.assertThat;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;

/**
 * Helper routines for all test classes.
 */
public final class TestHelper {

    /**
     * Private constructor to vaoid external instanciation.
     */
    private TestHelper() {
        super();
    }

    /**
     * Asserts the two properties files contain the same content.
     * 
     * @param fileA
     *            First properties file.
     * @param fileB
     *            First properties file.
     */
    public static void assertPropertiesEqual(final File fileA, final File fileB) {
        final Properties propsA = Utils4J.loadProperties(fileA);
        final Properties propsB = Utils4J.loadProperties(fileA);
        assertEqual(propsA, propsB);
    }

    /**
     * Asserts the two properties contain the same content.
     * 
     * @param fileA
     *            First properties file.
     * @param propsB
     *            First properties.
     */
    public static void assertPropertiesEqual(final File fileA, final Properties propsB) {
        final Properties propsA = Utils4J.loadProperties(fileA);
        assertEqual(propsA, propsB);
    }

    /**
     * Asserts the two properties contain the same content.
     * 
     * @param propsA
     *            First properties.
     * @param propsB
     *            First properties.
     */
    public static void assertEqual(final Properties propsA, final Properties propsB) {
        assertThat(propsA.size()).isEqualTo(propsB.size());
        final Iterator<Object> it = propsA.keySet().iterator();
        while (it.hasNext()) {
            final String key = (String) it.next();
            final String valueA = (String) propsA.get(key);
            final String valueB = (String) propsB.get(key);
            assertThat(valueA).isEqualTo(valueB);
        }
    }

}
