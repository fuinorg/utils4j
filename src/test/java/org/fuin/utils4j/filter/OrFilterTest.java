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
import static org.assertj.core.api.Assertions.assertThat;

//CHECKSTYLE:OFF
public class OrFilterTest extends ListFilterTest {

    @Override
    protected final ListFilter createTestee(final String name) {
        final OrFilter filter = new OrFilter();
        filter.setOrStr(name);
        return filter;
    }

    @Test
    public final void testConstructor() {
        final Filter filterA = new DummyFilter();
        final Filter filterB = new DummyFilter();
        final OrFilter filter = new OrFilter(filterA, filterB);
        assertThat(filter.getFilterList()).isNotNull();
        assertThat(filter.getFilterList().size()).isEqualTo(2);
        assertThat(filter.getFilterList().get(0)).isEqualTo(filterA);
        assertThat(filter.getFilterList().get(1)).isEqualTo(filterB);
    }

    @Test
    public final void testComplies() {

        final Filter filterTRUE = new DummyFilter("true", true);
        final Filter filterFALSE = new DummyFilter("false", false);

        assertThat((new OrFilter(filterTRUE, filterTRUE)).complies("Does not matter")).isTrue();
        assertThat((new OrFilter(filterTRUE, filterFALSE)).complies("Does not matter")).isTrue();
        assertThat((new OrFilter(filterFALSE, filterTRUE)).complies("Does not matter")).isTrue();
        assertThat((new OrFilter(filterFALSE, filterFALSE)).complies("Does not matter")).isFalse();

    }

    @Test
    public final void testSetGetAndStr() {
        final OrFilter filter = new OrFilter();
        filter.setOrStr("||");
        assertThat(filter.getOrStr()).isEqualTo("||");
    }

    @Test
    public final void testToString() {
        final Filter filterA = new DummyFilter("A");
        final Filter filterB = new DummyFilter("B");
        final Filter filterC = new DummyFilter("C");
        final OrFilter filter = new OrFilter(filterA, filterB);

        filter.setOrStr("||");
        assertThat(filter.toString()).isEqualTo("(A || B)");

        filter.setOrStr("or");
        filter.setOpenBracket("[");
        filter.setCloseBracket("]");
        assertThat(filter.toString()).isEqualTo("[A or B]");

        filter.addFilter(filterC);
        assertThat(filter.toString()).isEqualTo("[A or B or C]");
    }

}
// CHECKSTYLE:ON
