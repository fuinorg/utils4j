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
public class BooleanFilterTest {

    @Test
    public final void testCreateAndGet() {
        assertThat((new BooleanFilter(Boolean.TRUE)).getConstValue()).isEqualTo(Boolean.TRUE);
        assertThat((new BooleanFilter(Boolean.FALSE)).getConstValue()).isEqualTo(Boolean.FALSE);
    }

    @Test
    public final void testComplies() {
        final Filter trueFilter = new BooleanFilter(Boolean.TRUE);
        final Filter falseFilter = new BooleanFilter(Boolean.FALSE);
        assertThat(trueFilter.complies(Boolean.TRUE)).isTrue();
        assertThat(trueFilter.complies(Boolean.FALSE)).isFalse();
        assertThat(falseFilter.complies(Boolean.FALSE)).isTrue();
        assertThat(falseFilter.complies(Boolean.TRUE)).isFalse();
    }

    @Test
    public final void testToString() {
        final Filter trueFilter = new BooleanFilter(Boolean.TRUE);
        final Filter falseFilter = new BooleanFilter(Boolean.FALSE);
        assertThat(trueFilter.toString()).isEqualTo(" = true");
        assertThat(falseFilter.toString()).isEqualTo(" = false");
    }

}
// CHECKSTYLE:ON
