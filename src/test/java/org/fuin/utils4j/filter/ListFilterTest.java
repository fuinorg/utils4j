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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//CHECKSTYLE:OFF
public abstract class ListFilterTest {

    protected abstract ListFilter createTestee(String name);

    @Test
    public final void testAddRemoveFilter() {
        final ListFilter filter = createTestee(" x ");
        assertThat(filter.getFilterList()).isEmpty();

        final DummyFilter subFilter1 = new DummyFilter();
        filter.addFilter(subFilter1);
        assertThat(filter.getFilterList().size()).isEqualTo(1);
        assertThat(filter.getFilterList().get(0)).isEqualTo(subFilter1);

        final DummyFilter subFilter2 = new DummyFilter();
        filter.addFilter(subFilter2);
        assertThat(filter.getFilterList().size()).isEqualTo(2);
        assertThat(filter.getFilterList().get(0)).isEqualTo(subFilter1);
        assertThat(filter.getFilterList().get(1)).isEqualTo(subFilter2);

        // remove first filter from list
        filter.removeFilter(subFilter1);
        assertThat(filter.getFilterList().size()).isEqualTo(1);
        assertThat(filter.getFilterList().get(0)).isEqualTo(subFilter2);

        // remove second filter from list
        filter.removeFilter(subFilter2);
        assertThat(filter.getFilterList()).isEmpty();

    }

    @Test
    public final void testToStringString() {
        final ListFilter filter = createTestee(" x ");
        final DummyFilter subFilterA = new DummyFilter("A");
        filter.addFilter(subFilterA);

        final DummyFilter subFilterB = new DummyFilter("B");
        filter.addFilter(subFilterB);

        assertThat(filter.toString()).isEqualTo("(A  x  B)");

        final DummyFilter subFilterC = new DummyFilter("C");
        filter.addFilter(subFilterC);

        assertThat(filter.toString()).isEqualTo("(A  x  B  x  C)");

    }

    @Test
    public final void testSetGetCloseBracket() {
        final ListFilter filter = createTestee(" x ");
        filter.setCloseBracket("[");
        assertThat(filter.getCloseBracket()).isEqualTo("[");
    }

    @Test
    public final void testSetGetOpenBracket() {
        final ListFilter filter = createTestee(" x ");
        filter.setOpenBracket("[");
        assertThat(filter.getOpenBracket()).isEqualTo("[");
    }

    @Test
    public final void testSetGetFilterList() {
        final ListFilter filter = createTestee(" x ");
        final List<Filter> list = new ArrayList<>();
        list.add(new DummyFilter());
        filter.setFilterList(list);
        assertThat(filter.getFilterList()).isEqualTo(list);
    }

    // Add first filter to list
    protected static class DummyFilter implements Filter {
        private String name = "XYZ";

        private boolean value = false;

        public DummyFilter() {
            super();
        }

        public DummyFilter(final String name) {
            super();
            this.name = name;
        }

        public DummyFilter(final String name, final boolean value) {
            super();
            this.name = name;
            this.value = value;
        }

        @Override
        public final boolean complies(final Object obj) {
            return value;
        }

        @Override
        public final String toString() {
            return name;
        }
    }

}
// CHECKSTYLE:ON
