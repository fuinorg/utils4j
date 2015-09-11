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

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for IllegalNullArgumentException.
 */
//CHECKSTYLE:OFF
public class IllegalNullArgumentExceptionTest {

    @Test
    public final void testConstruction() {
        final String argument = "xyz123";
        final IllegalNullArgumentException ex = new IllegalNullArgumentException(
                argument);
        assertThat(ex.getArgument()).isEqualTo(argument);
        assertThat(ex.getMessage()).isEqualTo(
                "The argument '" + argument + "' cannot be null!");
        assertThat(ex.getCause()).isNull();
    }

}
//CHECKSTYLE:ON
