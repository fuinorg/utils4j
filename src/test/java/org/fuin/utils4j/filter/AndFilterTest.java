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
public class AndFilterTest extends ListFilterTest {

    protected final ListFilter createTestee(final String name) {
        final AndFilter filter = new AndFilter();
        filter.setAndStr(name);
        return filter;
    }

    @Test
    public final void testConstructor() {
        final Filter filterA = new DummyFilter();
        final Filter filterB = new DummyFilter();
        final AndFilter filter = new AndFilter(filterA, filterB);
        assertThat(filter.getFilterList()).isNotNull();
        assertThat(filter.getFilterList()).hasSize(2);
        assertThat(filter.getFilterList().get(0)).isEqualTo(filterA);
        assertThat(filter.getFilterList().get(1)).isEqualTo(filterB);
    }

    @Test
    public final void testComplies() {

        final Filter filterTRUE = new DummyFilter("true", true);
        final Filter filterFALSE = new DummyFilter("false", false);

        assertThat(
                (new AndFilter(filterTRUE, filterTRUE))
                        .complies("Does not matter")).isTrue();
        assertThat(
                (new AndFilter(filterTRUE, filterFALSE))
                        .complies("Does not matter")).isFalse();
        assertThat(
                (new AndFilter(filterFALSE, filterTRUE))
                        .complies("Does not matter")).isFalse();
        assertThat(
                (new AndFilter(filterFALSE, filterFALSE))
                        .complies("Does not matter")).isFalse();

    }

    @Test
    public final void testSetGetAndStr() {
        final AndFilter filter = new AndFilter();
        filter.setAndStr("&&");
        assertThat(filter.getAndStr()).isEqualTo("&&");
    }

    @Test
    public final void testToString() {
        final Filter filterA = new DummyFilter("A");
        final Filter filterB = new DummyFilter("B");
        final Filter filterC = new DummyFilter("C");
        final AndFilter filter = new AndFilter(filterA, filterB);

        filter.setAndStr("&&");
        assertThat(filter.toString()).isEqualTo("(A && B)");

        filter.setAndStr("and");
        filter.setOpenBracket("[");
        filter.setCloseBracket("]");
        assertThat(filter.toString()).isEqualTo("[A and B]");

        filter.addFilter(filterC);
        assertThat(filter.toString()).isEqualTo("[A and B and C]");
    }

}
// CHECKSTYLE:ON
