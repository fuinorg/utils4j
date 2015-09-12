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

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.junit.Test;

//CHECKSTYLE:OFF
public class StringPropertyFilterTest extends PropertyFilterTest {

    @Override
    protected final PropertyFilter createTestee(final String propertyName) {
        return new StringPropertyFilter(propertyName, StringFilter.Operator.EQ, "One");
    }

    @Test
    public final void testCompliesLT() {
        final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.LT, "2");
        assertThat(filter.complies(new TestObject("1"))).isTrue();
        assertThat(filter.complies(new TestObject("2"))).isFalse();
        assertThat(filter.complies(new TestObject("3"))).isFalse();
    }

    @Test
    public final void testCompliesLTE() {
        final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.LTE, "2");
        assertThat(filter.complies(new TestObject("1"))).isTrue();
        assertThat(filter.complies(new TestObject("2"))).isTrue();
        assertThat(filter.complies(new TestObject("3"))).isFalse();
    }

    @Test
    public final void testCompliesEQ() {
        final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.EQ, "2");
        assertThat(filter.complies(new TestObject("1"))).isFalse();
        assertThat(filter.complies(new TestObject("2"))).isTrue();
        assertThat(filter.complies(new TestObject("3"))).isFalse();
    }

    @Test
    public final void testCompliesEQRELAXED() {
        Filter filter;
        filter = new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.EQ_RELAXED, "abc");
        assertThat(filter.complies(new TestObject("ab"))).isFalse();
        assertThat(filter.complies(new TestObject("abc"))).isTrue();
        assertThat(filter.complies(new TestObject("abcd"))).isTrue();
        assertThat(filter.complies(new TestObject("abcde"))).isTrue();
    }

    @Test
    public final void testCompliesGT() {
        final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.GT, "2");
        assertThat(filter.complies(new TestObject("1"))).isFalse();
        assertThat(filter.complies(new TestObject("2"))).isFalse();
        assertThat(filter.complies(new TestObject("3"))).isTrue();
    }

    @Test
    public final void testCompliesGTE() {
        final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.GTE, "2");
        assertThat(filter.complies(new TestObject("1"))).isFalse();
        assertThat(filter.complies(new TestObject("2"))).isTrue();
        assertThat(filter.complies(new TestObject("3"))).isTrue();
    }

    @Test
    public final void testToString() {
        assertThat("" + new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.LT, "2"))
                .isEqualTo(STRING_PROPERTY_NAME + " < '2'");
        assertThat("" + new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.LTE, "2"))
                .isEqualTo(STRING_PROPERTY_NAME + " <= '2'");
        assertThat("" + new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.EQ, "2"))
                .isEqualTo(STRING_PROPERTY_NAME + " = '2'");
        assertThat("" + new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.EQ_RELAXED, "2"))
                .isEqualTo(STRING_PROPERTY_NAME + " ~ '2'");
        assertThat("" + new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.GT, "2"))
                .isEqualTo(STRING_PROPERTY_NAME + " > '2'");
        assertThat("" + new StringPropertyFilter(STRING_PROPERTY_NAME, StringFilter.Operator.GTE, "2"))
                .isEqualTo(STRING_PROPERTY_NAME + " >= '2'");
    }

}
// CHECKSTYLE:ON
