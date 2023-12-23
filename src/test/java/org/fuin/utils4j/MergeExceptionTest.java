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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

//CHECKSTYLE:OFF
public final class MergeExceptionTest {

    @Test
    public final void testConstruction() {
        final File file = new File("/tmp/test.txt");
        final String text = "ABC";
        final Property prop = new Property("A", "-1-", "1");
        final Property fileProp = new Property("B", "-2-", "2");
        final MergeException.Problem[] problems = new MergeException.Problem[] { new MergeException.Problem(text, prop, fileProp) };
        final MergeException testee = new MergeException(file, problems);
        assertThat(testee.getFile()).isEqualTo(file);
        assertThat(testee.getProblems()).containsExactly(problems);
    }

    @Test
    public final void testSerDeserialize() {

        // PREPARE
        final File file = new File("/tmp/test.txt");
        final String text = "ABC";
        final Property prop = new Property("A", "-1-", "1");
        final Property fileProp = new Property("B", "-2-", "2");
        final MergeException.Problem[] problems = new MergeException.Problem[] { new MergeException.Problem(text, prop, fileProp) };
        final MergeException testee = new MergeException(file, problems);

        // TEST
        final byte[] data = Utils4J.serialize(testee);
        final MergeException copy = Utils4J.deserialize(data);

        // VERIFY
        assertThat(testee.getFile()).isEqualTo(file);
        assertThat(testee.getProblems()).containsExactly(problems);

    }

}
// CHECKSTYLE:ON
