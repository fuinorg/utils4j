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
public class RegExprFilterTest {

    @Test
    public final void testSetGetType() {
        final RegExprFilter filter = new RegExprFilter("a*b");

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
        final RegExprFilter filter = new RegExprFilter("a*b");

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

        final RegExprFilter filter1 = new RegExprFilter("a*b");
        filter1.setType(RegExprFilter.MATCHES);
        assertThat(filter1.complies("ab")).isTrue();
        assertThat(filter1.complies("aab")).isTrue();
        assertThat(filter1.complies("aaab")).isTrue();
        assertThat(filter1.complies("aaabb")).isFalse();
        assertThat(filter1.complies("abc")).isFalse();
        assertThat(filter1.complies("bab")).isFalse();
        assertThat(filter1.complies("xbab")).isFalse();

        final RegExprFilter filter2 = new RegExprFilter(".*schlemmerinfo\\.de.*");
        filter2.setType(RegExprFilter.MATCHES);
        assertThat(filter2.complies("http://www.schlemmerinfo.de/eng/")).isTrue();
        assertThat(filter2.complies("http://www.schlemmerinfo.de/eng/hamburg/")).isTrue();
        assertThat(filter2.complies("http://www.schlemmerinfo.de/deu")).isTrue();
        assertThat(filter2.complies("www.schlemmerinfo.de")).isTrue();

    }

    @Test
    public final void testCompliesLookingAt() {

        final RegExprFilter filter1 = new RegExprFilter("a*b");
        filter1.setType(RegExprFilter.LOOKING_AT);
        assertThat(filter1.complies("ab")).isTrue();
        assertThat(filter1.complies("aab")).isTrue();
        assertThat(filter1.complies("aaab")).isTrue();
        assertThat(filter1.complies("aaabb")).isTrue();
        assertThat(filter1.complies("abc")).isTrue();
        assertThat(filter1.complies("bab")).isTrue();
        assertThat(filter1.complies("xbab")).isFalse();

        final RegExprFilter filter2 = new RegExprFilter(".*schlemmerinfo\\.de.*");
        filter2.setType(RegExprFilter.LOOKING_AT);
        assertThat(filter2.complies("http://www.schlemmerinfo.de/eng/")).isTrue();
        assertThat(filter2.complies("http://www.schlemmerinfo.de/eng/hamburg/")).isTrue();
        assertThat(filter2.complies("http://www.schlemmerinfo.de/deu")).isTrue();
        assertThat(filter2.complies("www.schlemmerinfo.de")).isTrue();

    }

    @Test
    public final void testCompliesFind() {

        final RegExprFilter filter1 = new RegExprFilter("a*b");
        filter1.setType(RegExprFilter.FIND);
        assertThat(filter1.complies("ab")).isTrue();
        assertThat(filter1.complies("aab")).isTrue();
        assertThat(filter1.complies("aaab")).isTrue();
        assertThat(filter1.complies("aaabb")).isTrue();
        assertThat(filter1.complies("abc")).isTrue();
        assertThat(filter1.complies("bab")).isTrue();
        assertThat(filter1.complies("xbab")).isTrue();

        final RegExprFilter filter2 = new RegExprFilter(".*schlemmerinfo\\.de.*");
        filter2.setType(RegExprFilter.FIND);
        assertThat(filter2.complies("http://www.schlemmerinfo.de/eng/")).isTrue();
        assertThat(filter2.complies("http://www.schlemmerinfo.de/eng/hamburg/")).isTrue();
        assertThat(filter2.complies("http://www.schlemmerinfo.de/deu")).isTrue();
        assertThat(filter2.complies("www.schlemmerinfo.de")).isTrue();

    }

}
// CHECKSTYLE:ON
