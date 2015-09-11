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

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

//CHECKSTYLE:OFF
public class TokenPropertyFilterTest extends PropertyFilterTest {

    protected final PropertyFilter createTestee(final String propertyName) {
        return new TokenPropertyFilter(propertyName, "one", ";");
    }

    @Test
    public final void testCompliesObject() {
        Filter testee;

        testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "one", ",");
        assertThat(testee.complies((new TestObject("one")))).isTrue();
        assertThat(testee.complies((new TestObject("one,two")))).isTrue();
        assertThat(testee.complies((new TestObject("one,two,three")))).isTrue();
        assertThat(testee.complies((new TestObject("two,one,three")))).isTrue();
        assertThat(testee.complies((new TestObject("two,three,one")))).isTrue();
        assertThat(testee.complies((new TestObject("two,one")))).isTrue();

        testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "one", ";");
        assertThat(testee.complies((new TestObject("one")))).isTrue();
        assertThat(testee.complies((new TestObject("one;two")))).isTrue();
        assertThat(testee.complies((new TestObject("one;two;three")))).isTrue();
        assertThat(testee.complies((new TestObject("two;one;three")))).isTrue();
        assertThat(testee.complies((new TestObject("two;three;one")))).isTrue();
        assertThat(testee.complies((new TestObject("two;one")))).isTrue();

        testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "four", ";");
        assertThat(testee.complies((new TestObject("one;two;three"))))
                .isFalse();

        testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "x", ";");
        assertThat(testee.complies((new TestObject("one;two;three"))))
                .isFalse();

        testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, " ", ";");
        assertThat(testee.complies((new TestObject("one;two;three; ;five"))))
                .isTrue();

    }

    @Test
    public final void testToString() {
        assertThat(
                "" + new TokenPropertyFilter(STRING_PROPERTY_NAME, "one", ","))
                .isEqualTo(STRING_PROPERTY_NAME + " contains 'one' [,]");
    }

}
// CHECKSTYLE:ON
