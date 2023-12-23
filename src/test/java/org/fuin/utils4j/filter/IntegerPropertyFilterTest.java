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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

//CHECKSTYLE:OFF
public class IntegerPropertyFilterTest extends PropertyFilterTest {

    @Override
    protected final PropertyFilter createTestee(final String propertyName) {
        return new IntegerPropertyFilter(propertyName, ComparableFilter.Operator.EQ, Integer.valueOf(1));
    }

    @Test
    public final void testCompliesLT() {
        final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.LT, Integer.valueOf(2));
        assertThat(filter.complies(new TestObject(Integer.valueOf(1)))).isTrue();
        assertThat(filter.complies(new TestObject(Integer.valueOf(2)))).isFalse();
        assertThat(filter.complies(new TestObject(Integer.valueOf(3)))).isFalse();
    }

    @Test
    public final void testCompliesLTE() {
        final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.LTE, Integer.valueOf(2));
        assertThat(filter.complies(new TestObject(Integer.valueOf(1)))).isTrue();
        assertThat(filter.complies(new TestObject(Integer.valueOf(2)))).isTrue();
        assertThat(filter.complies(new TestObject(Integer.valueOf(3)))).isFalse();
    }

    @Test
    public final void testCompliesEQ() {
        final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.EQ, Integer.valueOf(2));
        assertThat(filter.complies(new TestObject(Integer.valueOf(1)))).isFalse();
        assertThat(filter.complies(new TestObject(Integer.valueOf(2)))).isTrue();
        assertThat(filter.complies(new TestObject(Integer.valueOf(3)))).isFalse();
    }

    @Test
    public final void testCompliesGT() {
        final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.GT, Integer.valueOf(2));
        assertThat(filter.complies(new TestObject(Integer.valueOf(1)))).isFalse();
        assertThat(filter.complies(new TestObject(Integer.valueOf(2)))).isFalse();
        assertThat(filter.complies(new TestObject(Integer.valueOf(3)))).isTrue();
    }

    @Test
    public final void testCompliesGTE() {
        final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.GTE, Integer.valueOf(2));
        assertThat(filter.complies(new TestObject(Integer.valueOf(1)))).isFalse();
        assertThat(filter.complies(new TestObject(Integer.valueOf(2)))).isTrue();
        assertThat(filter.complies(new TestObject(Integer.valueOf(3)))).isTrue();
    }

    @Test
    public final void testToString() {
        assertThat("" + new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.LT, Integer.valueOf(2)))
                .isEqualTo(INTEGER_PROPERTY_NAME + " < 2");
        assertThat("" + new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.LTE, Integer.valueOf(2)))
                .isEqualTo(INTEGER_PROPERTY_NAME + " <= 2");
        assertThat("" + new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.EQ, Integer.valueOf(2)))
                .isEqualTo(INTEGER_PROPERTY_NAME + " = 2");
        assertThat("" + new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.GT, Integer.valueOf(2)))
                .isEqualTo(INTEGER_PROPERTY_NAME + " > 2");
        assertThat("" + new IntegerPropertyFilter(INTEGER_PROPERTY_NAME, ComparableFilter.Operator.GTE, Integer.valueOf(2)))
                .isEqualTo(INTEGER_PROPERTY_NAME + " >= 2");
    }

}
// CHECKSTYLE:ON
