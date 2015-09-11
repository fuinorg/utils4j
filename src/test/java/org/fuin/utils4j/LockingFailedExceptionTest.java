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

import org.junit.Test;

// CHECKSTYLE:OFF
public class LockingFailedExceptionTest {

    @Test
    public final void testConstructionMessage() {
        final String message = "xyz123";
        final LockingFailedException ex = new LockingFailedException(message);
        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex.getCause()).isNull();
    }

    @Test
    public final void testConstructionMessageCause() {
        final IllegalArgumentException cause = new IllegalArgumentException(
                "Test");
        final String message = "xyz123";
        final LockingFailedException ex = new LockingFailedException(message,
                cause);
        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

}
// CHECKSTYLE:ON
