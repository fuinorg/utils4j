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
package org.fuin.utils4j.fileprocessor;

import static org.assertj.core.api.Assertions.assertThat;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

// CHECKSTYLE:OFF
public class FileOrderTest {

    @Test
    public final void testFromName() {

        assertThat(FileOrder.fromName("FILES_FIRST")).isSameAs(FileOrder.FILES_FIRST);
        
    }

    @Test
    public final void testIsValid() {
        
        assertThat(FileOrder.isValid("FILES_FIRST")).isTrue();
        assertThat(FileOrder.isValid("FOO")).isFalse();
    }
    
    @Test
    public final void testEqualsHashCode() {

        EqualsVerifier.forClass(FileOrder.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();

    }
    
}
// CHECKSTYLE:ON
