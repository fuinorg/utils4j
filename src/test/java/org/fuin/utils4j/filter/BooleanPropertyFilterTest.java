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
package org.fuin.utils4j.filter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//CHECKSTYLE:OFF
public class BooleanPropertyFilterTest extends PropertyFilterTest {

    private static final String PROPERTY_NAME = "flag";

    @Override
    protected final PropertyFilter createTestee(final String propertyName) {
        return new BooleanPropertyFilter(propertyName, Boolean.TRUE);
    }

    @Test
    public final void testCreateAndGet() {
        final BooleanPropertyFilter filterTRUE = new BooleanPropertyFilter(PROPERTY_NAME, Boolean.TRUE);
        assertThat(filterTRUE.getConstValue()).isTrue();
        assertThat(filterTRUE.getPropertyName()).isEqualTo(PROPERTY_NAME);

        final BooleanPropertyFilter filterFALSE = new BooleanPropertyFilter(PROPERTY_NAME, Boolean.FALSE);
        assertThat(filterFALSE.getConstValue()).isFalse();
        assertThat(filterFALSE.getPropertyName()).isEqualTo(PROPERTY_NAME);
    }

    @Test
    public final void testComplies() {
        final Filter trueFilter = new BooleanPropertyFilter(PROPERTY_NAME, Boolean.TRUE);
        final Filter falseFilter = new BooleanPropertyFilter(PROPERTY_NAME, Boolean.FALSE);
        final TestObject trueObj = new TestObject(Boolean.TRUE);
        final TestObject falseObj = new TestObject(Boolean.FALSE);
        assertThat(trueFilter.complies(trueObj)).isTrue();
        assertThat(trueFilter.complies(falseObj)).isFalse();
        assertThat(falseFilter.complies(falseObj)).isTrue();
        assertThat(falseFilter.complies(trueObj)).isFalse();
    }

    @Test
    public final void testToString() {
        final Filter trueFilter = new BooleanPropertyFilter(PROPERTY_NAME, Boolean.TRUE);
        final Filter falseFilter = new BooleanPropertyFilter(PROPERTY_NAME, Boolean.FALSE);

        assertThat(trueFilter.toString()).isEqualTo(PROPERTY_NAME + " = true");
        assertThat(falseFilter.toString()).isEqualTo(PROPERTY_NAME + " = false");

    }

}
// CHECKSTYLE:ON
