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
public class TokenFilterTest {

    @Test
    public final void testCompliesObject() {
        Filter testee;

        testee = new TokenFilter("one", ",");
        assertThat(testee.complies("one")).isTrue();
        assertThat(testee.complies("one,two")).isTrue();
        assertThat(testee.complies("one,two,three")).isTrue();
        assertThat(testee.complies("two,one,three")).isTrue();
        assertThat(testee.complies("two,three,one")).isTrue();
        assertThat(testee.complies("two,one")).isTrue();

        testee = new TokenFilter("one", ";");
        assertThat(testee.complies("one")).isTrue();
        assertThat(testee.complies("one;two")).isTrue();
        assertThat(testee.complies("one;two;three")).isTrue();
        assertThat(testee.complies("two;one;three")).isTrue();
        assertThat(testee.complies("two;three;one")).isTrue();
        assertThat(testee.complies("two;one")).isTrue();

        testee = new TokenFilter("four", ";");
        assertThat(testee.complies("one;two;three")).isFalse();

        testee = new TokenFilter("x", ";");
        assertThat(testee.complies("one;two;three")).isFalse();

        testee = new TokenFilter(" ", ";");
        assertThat(testee.complies("one;two;three; ;five")).isTrue();

    }

    @Test
    public final void testToString() {
        assertThat("" + new TokenFilter("one", ",")).isEqualTo(" contains 'one' [,]");
    }

}
// CHECKSTYLE:ON
