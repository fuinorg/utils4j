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

import org.junit.Test;

//CHECKSTYLE:OFF
public class RegExprPropertyFilterTest extends PropertyFilterTest {

    @Override
    protected final PropertyFilter createTestee(final String propertyName) {
        return new RegExprPropertyFilter(propertyName, "a*b");
    }

    @Test
    public final void testSetGetType() {
        final RegExprPropertyFilter filter = new RegExprPropertyFilter(STRING_PROPERTY_NAME, "a*b");

        filter.setType(RegExprFilter.MATCHES);
        assertThat(filter.getType()).isEqualTo(RegExprFilter.MATCHES);
        assertThat(filter.getTypeName()).isEqualTo("matches");

        filter.setType(RegExprFilter.LOOKING_AT);
        assertThat(filter.getType()).isEqualTo(RegExprFilter.LOOKING_AT);
        assertThat(filter.getTypeName()).isNull();

        filter.setType(RegExprFilter.FIND);
        assertThat(filter.getType()).isEqualTo(RegExprFilter.FIND);
        assertThat(filter.getTypeName()).isEqualTo("find");
    }

    @Test
    public final void testSetGetTypeName() {
        final RegExprPropertyFilter filter = new RegExprPropertyFilter(STRING_PROPERTY_NAME, "a*b");

        filter.setTypeName("matches");
        assertThat(filter.getTypeName()).isEqualTo("matches");
        assertThat(filter.getType()).isEqualTo(RegExprFilter.MATCHES);

        filter.setTypeName("lookingAt");
        assertThat(filter.getTypeName()).isNull();
        assertThat(filter.getType()).isEqualTo(RegExprFilter.LOOKING_AT);

        filter.setTypeName("find");
        assertThat(filter.getTypeName()).isEqualTo("find");
        assertThat(filter.getType()).isEqualTo(RegExprFilter.FIND);

    }

    @Test
    public final void testCompliesMatches() {

        final RegExprPropertyFilter filter1 = new RegExprPropertyFilter(STRING_PROPERTY_NAME, "a*b");
        filter1.setType(RegExprFilter.MATCHES);
        assertThat(filter1.complies(new TestObject("ab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aaab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aaabb"))).isFalse();
        assertThat(filter1.complies(new TestObject("abc"))).isFalse();
        assertThat(filter1.complies(new TestObject("bab"))).isFalse();
        assertThat(filter1.complies(new TestObject("xbab"))).isFalse();

        final RegExprPropertyFilter filter2 = new RegExprPropertyFilter(STRING_PROPERTY_NAME,
                ".*schlemmerinfo\\.de.*");
        filter2.setType(RegExprFilter.MATCHES);
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/eng/"))).isTrue();
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/eng/hamburg/"))).isTrue();
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/deu"))).isTrue();
        assertThat(filter2.complies(new TestObject("www.schlemmerinfo.de"))).isTrue();

    }

    @Test
    public final void testCompliesLookingAt() {

        final RegExprPropertyFilter filter1 = new RegExprPropertyFilter(STRING_PROPERTY_NAME, "a*b");
        filter1.setType(RegExprFilter.LOOKING_AT);
        assertThat(filter1.complies(new TestObject("ab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aaab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aaabb"))).isTrue();
        assertThat(filter1.complies(new TestObject("abc"))).isTrue();
        assertThat(filter1.complies(new TestObject("bab"))).isTrue();
        assertThat(filter1.complies(new TestObject("xbab"))).isFalse();

        final RegExprPropertyFilter filter2 = new RegExprPropertyFilter(STRING_PROPERTY_NAME,
                ".*schlemmerinfo\\.de.*");
        filter2.setType(RegExprFilter.LOOKING_AT);
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/eng/"))).isTrue();
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/eng/hamburg/"))).isTrue();
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/deu"))).isTrue();
        assertThat(filter2.complies(new TestObject("www.schlemmerinfo.de"))).isTrue();

    }

    @Test
    public final void testCompliesFind() {

        final RegExprPropertyFilter filter1 = new RegExprPropertyFilter(STRING_PROPERTY_NAME, "a*b");
        filter1.setType(RegExprFilter.FIND);
        assertThat(filter1.complies(new TestObject("ab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aaab"))).isTrue();
        assertThat(filter1.complies(new TestObject("aaabb"))).isTrue();
        assertThat(filter1.complies(new TestObject("abc"))).isTrue();
        assertThat(filter1.complies(new TestObject("bab"))).isTrue();
        assertThat(filter1.complies(new TestObject("xbab"))).isTrue();

        final RegExprPropertyFilter filter2 = new RegExprPropertyFilter(STRING_PROPERTY_NAME,
                ".*schlemmerinfo\\.de.*");
        filter2.setType(RegExprFilter.FIND);
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/eng/"))).isTrue();
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/eng/hamburg/"))).isTrue();
        assertThat(filter2.complies(new TestObject("http://www.schlemmerinfo.de/deu"))).isTrue();
        assertThat(filter2.complies(new TestObject("www.schlemmerinfo.de"))).isTrue();

    }

}
// CHECKSTYLE:ON
