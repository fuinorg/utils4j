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
package org.fuin.utils4j.examples;

import org.fuin.utils4j.Utils4J;

import java.net.URL;

/**
 * Short example for creating a "classpath:" scheme URL.
 */
// CHECKSTYLE:OFF
public class ClasspathURLExample {

    public static void main(final String[] args) {

        // Create URL using a utility method
        final URL url = Utils4J.url("classpath:org/fuin/utils4j/test.properties");

        // Read content of URL into to a string
        final String text = Utils4J.readAsString(url, "utf-8", 1024);

        // Output:
        System.out.println(text);
        // one=1
        // two=2
        // three=3

    }

}
// CHECKSTYLE:ON
